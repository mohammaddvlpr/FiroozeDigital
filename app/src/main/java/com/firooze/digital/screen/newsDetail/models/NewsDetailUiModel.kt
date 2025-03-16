package com.firooze.digital.screen.newsDetail.models

data class NewsDetailUiModel(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val dateAndTime: String,
    val content: String
)
