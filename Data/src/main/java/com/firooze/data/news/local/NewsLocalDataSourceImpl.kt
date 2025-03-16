package com.firooze.data.news.local

import androidx.paging.PagingSource
import com.firooze.data.news.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(private val newsDao: NewsDao) :
    NewsLocalDataSource {
    override fun getNewsNewerThan(dateTime: String): Flow<Int> {
        return newsDao.getNewsNewerThan(dateTime)
    }

    override suspend fun insertNewsItems(items: List<NewsEntity>) {
        newsDao.insertNewsItems(items)
    }

    override fun getPagingSource(): PagingSource<Int, NewsEntity> {
        return newsDao.pagingSource()
    }

    override suspend fun getLastUpdateTimeMillis(): Long {
        return newsDao.getLastUpdateTimeMillis()
    }

    override suspend fun deleteAll() {
        return newsDao.deleteAll()
    }

    override suspend fun getOldestPublishDateTime(): String? {
        return newsDao.getOldestUpdatePublishDateTime()
    }

    override suspend fun getNewsById(id: String): NewsEntity? {
        return newsDao.getNewsById(id)
    }


}