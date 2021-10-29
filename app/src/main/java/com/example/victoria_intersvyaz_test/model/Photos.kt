package com.example.victoria_intersvyaz_test.model

import com.google.gson.annotations.SerializedName

data class Photos(
    val albumId: String? = null,
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    val url: String? = null,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,
)
