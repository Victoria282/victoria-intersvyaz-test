package com.example.victoria_intersvyaz_test.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.victoria_intersvyaz_test.api.ApiHelper
import com.example.victoria_intersvyaz_test.api.CommentApiState
import com.example.victoria_intersvyaz_test.api.RetrofitBuilder
import com.example.victoria_intersvyaz_test.model.Photos
import com.example.victoria_intersvyaz_test.repository.DataRepository
import com.example.victoria_intersvyaz_test.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {
    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: DataRepository = DataRepository(apiHelper)

   val commentState = MutableStateFlow(
       CommentApiState(
           Status.LOADING,
           Photos(), ""
       )
   )

    fun getNewComment(id:Int) {
        commentState.value = CommentApiState.loading()

        viewModelScope.launch {
            mainRepository.getPhotos(id)
                .catch {
                    commentState.value = CommentApiState.error(it.message.toString())
                }
                .collect {
                    commentState.value = CommentApiState.success(it.data)
                }
        }
    }
}