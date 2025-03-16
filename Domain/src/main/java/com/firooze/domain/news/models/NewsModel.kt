package com.firooze.domain.news.models

data class NewsModel(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val dateAndTime: String,
    val content: String
)
