package com.example.victoria_intersvyaz_test.model

import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("albumId")
    val albumId: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,
)
