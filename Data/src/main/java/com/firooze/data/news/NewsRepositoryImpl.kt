package com.firooze.data.news

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.firooze.data.db.AppDatabase
import com.firooze.data.network.NetworkStatusProvider
import com.firooze.data.news.local.NewsLocalDataSource
import com.firooze.data.news.remote.NewsRemoteDataSource
import com.firooze.domain.news.NewsRepository
import com.firooze.domain.news.models.NewsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource,
    private val networkStatusProvider: NetworkStatusProvider,
    private val appDatabase: AppDatabase,
    private val mapper: NewsDataMapper
) : NewsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllNewsPagingFlow(): Flow<PagingData<NewsModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = NewsPagingSource(
                newsLocalDataSource = newsLocalDataSource,
                newsRemoteDataSource = newsRemoteDataSource,
                appDatabase = appDatabase,
                mapper = mapper,
                networkStatusProvider = networkStatusProvider
            )
        ) {
            newsLocalDataSource.getPagingSource()
        }.flow.map { it.map { entity -> mapper.mapNewsEntityToDomain(entity) } }
    }

    override suspend fun getNewsDetailById(id: String): Result<NewsModel> {
        val fromLocal = newsLocalDataSource.getNewsById(id)

        return if (fromLocal != null)
            Result.success(mapper.mapNewsEntityToDomain(fromLocal))
        else
            Result.failure(Exception("No such entity in db!"))

    }

}