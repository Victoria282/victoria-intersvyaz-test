package com.example.victoria_intersvyaz_test.repository

import com.example.victoria_intersvyaz_test.api.ApiHelper

class DataRepository(private val apiHelper: ApiHelper) {
    suspend fun getPhotos() = apiHelper.getPhotos()
}