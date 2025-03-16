package com.firooze.data.news

import com.firooze.data.news.local.entity.NewsEntity
import com.firooze.data.news.remote.models.NewsApiModel
import com.firooze.domain.news.models.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsDataMapper @Inject constructor() {

    fun mapNewsEntityToDomain(newsEntity: NewsEntity) = with(newsEntity) {
        NewsModel(id, title, imageUrl, dateAndTime, content)
    }

    suspend fun mapNewsEntitiesToDomain(entities: List<NewsEntity>): List<NewsModel> {
        return withContext(Dispatchers.Default) {
            entities.map {
                with(it) {
                    NewsModel(id, title, imageUrl, dateAndTime, content)
                }
            }
        }
    }

    suspend fun mapNewsApiModelsToEntity(models: List<NewsApiModel>): List<NewsEntity> {
        return withContext(Dispatchers.Default) {
            models.map {
                with(it) {
                    NewsEntity(
                        id = url,
                        title = title,
                        imageUrl = imageUrl,
                        dateAndTime = publishDataAndTime,
                        saveTimeMillis = System.currentTimeMillis(),
                        content = content
                    )
                }
            }
        }
    }


}