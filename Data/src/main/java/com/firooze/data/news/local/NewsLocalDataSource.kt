package com.firooze.data.news.local

import androidx.paging.PagingSource
import com.firooze.data.news.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    fun getNewsNewerThan(dateTime: String): Flow<Int>

    suspend fun insertNewsItems(items: List<NewsEntity>)

    fun getPagingSource(): PagingSource<Int, NewsEntity>

    suspend fun getLastUpdateTimeMillis(): Long

    suspend fun deleteAll()

    suspend fun getOldestPublishDateTime(): String?

    suspend fun getNewsById(id: String): NewsEntity?

}