package com.firooze.data.news.remote.models

import com.google.gson.annotations.SerializedName

data class NewsApiModel(
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("content") val content: String,
    @SerializedName("title") val title: String,
    @SerializedName("publishedAt") val publishDataAndTime: String,

)
