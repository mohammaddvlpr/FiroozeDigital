package com.firooze.data.news.remote

import com.firooze.data.news.remote.models.NewsApiModel

interface NewsRemoteDataSource {

    suspend fun getNews(from: String? , to:String?): Result<List<NewsApiModel>>

}