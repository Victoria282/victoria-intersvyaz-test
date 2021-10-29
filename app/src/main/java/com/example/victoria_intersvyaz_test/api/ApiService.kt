package com.example.victoria_intersvyaz_test.api

import com.example.victoria_intersvyaz_test.model.Photos
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface ApiService {
    @GET("/photos/{id}")
    suspend fun getPhotos(@Path("id") id: Int): Photos
}