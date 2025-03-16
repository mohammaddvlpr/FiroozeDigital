package com.firooze.data.news.remote.models

import com.google.gson.annotations.SerializedName

data class NewsBatchApiModel(
    @SerializedName("articles") val models: List<NewsApiModel>
)
