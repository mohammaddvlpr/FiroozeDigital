package com.firooze.digital.screen.home.models

import com.firooze.digital.provider.dateTime.DateTimeProvider
import com.firooze.domain.news.models.NewsModel
import javax.inject.Inject

class HomeScreenUiMapper @Inject constructor(private val dateTimeProvider: DateTimeProvider) {

    fun mapDomainToUi(model: NewsModel) = with(model) {
        NewsUiModel(
            id,
            title,
            imageUrl,
            dateTimeProvider.formatDataTime(dateAndTime),
            content.take(200)
        )
    }
}