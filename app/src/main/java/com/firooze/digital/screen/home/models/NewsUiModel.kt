package com.firooze.digital.screen.home.models

data class NewsUiModel(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val dateAndTime: String,
    val summary: String
)
