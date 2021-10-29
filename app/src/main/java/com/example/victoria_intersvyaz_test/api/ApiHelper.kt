package com.example.victoria_intersvyaz_test.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPhotos(id:Int) = apiService.getPhotos(id)
}