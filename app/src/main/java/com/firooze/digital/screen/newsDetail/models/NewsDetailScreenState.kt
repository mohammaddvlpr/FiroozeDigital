package com.firooze.digital.screen.newsDetail.models

data class NewsDetailScreenState(
    val newsDetailUiModel: NewsDetailUiModel = NewsDetailUiModel(
        id = "0",
        title = "",
        imageUrl = null,
        dateAndTime = "",
        content = ""
    )
)
