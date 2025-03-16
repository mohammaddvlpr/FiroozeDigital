package com.firooze.digital.screen.newsDetail.models

import com.firooze.digital.provider.dateTime.DateTimeProvider
import com.firooze.domain.news.models.NewsModel
import javax.inject.Inject

class NewsDetailScreenMapper @Inject constructor(private val dateTimeProvider: DateTimeProvider) {

    fun mapNewModelToNewsDetailUiModel(model: NewsModel) = with(model) {
        NewsDetailUiModel(id, title, imageUrl, dateTimeProvider.formatDataTime(dateAndTime), content)
    }
}