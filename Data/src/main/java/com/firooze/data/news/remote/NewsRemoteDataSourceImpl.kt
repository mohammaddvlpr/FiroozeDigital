package com.firooze.data.news.remote

import com.firooze.data.apiCall
import com.firooze.data.news.remote.models.NewsApiModel
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(private val newsService: NewsService) :
    NewsRemoteDataSource {
    override suspend fun getNews(from: String?, to: String?): Result<List<NewsApiModel>> {
        return apiCall(call = { newsService.getNews(from , to) },
            map = { it.models })
    }
}