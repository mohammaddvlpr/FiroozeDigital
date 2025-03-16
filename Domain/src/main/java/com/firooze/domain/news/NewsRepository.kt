package com.firooze.domain.news

import androidx.paging.PagingData
import com.firooze.domain.news.models.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getAllNewsPagingFlow(): Flow<PagingData<NewsModel>>

    suspend fun getNewsDetailById(id:String):Result<NewsModel>

}