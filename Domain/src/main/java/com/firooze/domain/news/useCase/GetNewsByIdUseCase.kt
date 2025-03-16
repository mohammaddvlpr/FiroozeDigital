package com.firooze.domain.news.useCase

import com.firooze.domain.news.NewsRepository
import com.firooze.domain.news.models.NewsModel
import javax.inject.Inject

class GetNewsByIdUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(id: String): Result<NewsModel> {
        return newsRepository.getNewsDetailById(id)
    }
}