package com.example.victoria_intersvyaz_test.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.victoria_intersvyaz_test.api.ApiHelper
import com.example.victoria_intersvyaz_test.api.RetrofitBuilder
import com.example.victoria_intersvyaz_test.model.Photos
import com.example.victoria_intersvyaz_test.repository.DataRepository
import com.example.victoria_intersvyaz_test.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class ViewModel : ViewModel() {
    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: DataRepository = DataRepository(apiHelper)

    val myListPhotos: MutableLiveData<Response<Photos>> = MutableLiveData()

    fun getPhotos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPhotos()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}