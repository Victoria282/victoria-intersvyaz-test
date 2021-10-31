package com.example.victoria_intersvyaz_test.repository

import com.example.victoria_intersvyaz_test.api.ApiHelper
import com.example.victoria_intersvyaz_test.utils.CommentApiState
import com.example.victoria_intersvyaz_test.model.Photos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class DataRepository(private val apiHelper: ApiHelper) {
    suspend fun getPhotos(id:Int): Flow<CommentApiState<Photos>> {
        return flow {
            val photo: Photos = apiHelper.getPhotos(id)
            emit(CommentApiState.success(photo))
        }.flowOn(Dispatchers.IO)
    }
}