package com.example.victoria_intersvyaz_test.api

import com.example.victoria_intersvyaz_test.model.Photos
import retrofit2.http.*

interface ApiService {
    @GET("photos")
    suspend fun getPhotos(): List<Photos>
}