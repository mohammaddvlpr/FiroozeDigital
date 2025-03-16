package com.firooze.data.news.remote

import com.firooze.data.news.PAGE_SIZE
import com.firooze.data.news.remote.models.NewsBatchApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("everything?pageSize=$PAGE_SIZE&q=apple&sortBy=publishedAt")
    suspend fun getNews(
        @Query("from") fromDateTime: String?,
        @Query("to") to: String?
    ): NewsBatchApiModel

}