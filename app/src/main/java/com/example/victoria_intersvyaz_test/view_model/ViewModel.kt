package com.example.victoria_intersvyaz_test.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.victoria_intersvyaz_test.api.ApiHelper
import com.example.victoria_intersvyaz_test.api.CommentApiState
import com.example.victoria_intersvyaz_test.api.RetrofitBuilder
import com.example.victoria_intersvyaz_test.api.Status
import com.example.victoria_intersvyaz_test.model.Photos
import com.example.victoria_intersvyaz_test.repository.DataRepository
import com.example.victoria_intersvyaz_test.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel : ViewModel() {
    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: DataRepository = DataRepository(apiHelper)

    val myListPhotos: MutableLiveData<ArrayList<Photos>> = MutableLiveData()

    /*fun getPhotos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPhotos()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/

    val postData:MutableLiveData<List<Photos>> = MutableLiveData()

   /* fun getPhotos() {
        viewModelScope.launch {
            mainRepository.getPhotos().catch {
                System.out.println("VIKA ERROR")
            }.collect { response ->
                postData.value = response
            }
        }
    }*/
   val commentState = MutableStateFlow(
       CommentApiState(
           Status.LOADING,
           Photos(), ""
       )
   )

    // Function to get new Comments
    fun getNewComment(id:Int) {
        // Since Network Calls takes time,Set the
        // initial value to loading state
        commentState.value = CommentApiState.loading()

        // ApiCalls takes some time, So it has to be
        // run and background thread. Using viewModelScope
        // to call the api
        viewModelScope.launch {

            // Collecting the data emitted
            // by the function in repository
            mainRepository.getPhotos(id)
                // If any errors occurs like 404 not found
                // or invalid query, set the state to error
                // State to show some info
                // on screen
                .catch {
                    commentState.value =
                        CommentApiState.error(it.message.toString())
                }
                // If Api call is succeeded, set the State to Success
                // and set the response data to data received from api
                .collect {
                    commentState.value = CommentApiState.success(it.data)
                }
        }
    }
}