package com.firooze.domain.news.useCase

import androidx.paging.PagingData
import com.firooze.domain.news.NewsRepository
import com.firooze.domain.news.models.NewsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNewsPagingFlowUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(): Flow<PagingData<NewsModel>> {
        return newsRepository.getAllNewsPagingFlow()
    }
}