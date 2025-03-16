package com.firooze.data.news

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.firooze.data.db.AppDatabase
import com.firooze.data.network.NetworkStatusProvider
import com.firooze.data.news.local.NewsLocalDataSource
import com.firooze.data.news.local.entity.NewsEntity
import com.firooze.data.news.remote.NewsRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalPagingApi::class)
class NewsPagingSource(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val appDatabase: AppDatabase,
    private val mapper: NewsDataMapper,
    private val networkStatusProvider: NetworkStatusProvider
) : RemoteMediator<Int, NewsEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val toDate = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    newsLocalDataSource.getOldestPublishDateTime() ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
            }

            val date = Date(System.currentTimeMillis() - 2.days.inWholeMilliseconds)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fromDate = simpleDateFormat.format(date)

            val response = newsRemoteDataSource.getNews(
                from = fromDate , to = toDate
            )

            val responseValue = response.getOrNull()

            if (responseValue == null)
                MediatorResult.Error(response.exceptionOrNull() ?: Exception("Unknown"))
            else {
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        newsLocalDataSource.deleteAll()
                    }

                    newsLocalDataSource.insertNewsItems(
                        mapper.mapNewsApiModelsToEntity(
                            responseValue
                        )
                    )
                }

                MediatorResult.Success(
                    endOfPaginationReached = responseValue.size < PAGE_SIZE,
                )
            }

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = 5.minutes.inWholeMilliseconds
        val lastInsertedNewsTimeMillis: Long = newsLocalDataSource.getLastUpdateTimeMillis()

        return if ((System.currentTimeMillis() - lastInsertedNewsTimeMillis > cacheTimeout) &&
            networkStatusProvider.isInternetAvailable()
        )
            InitializeAction.LAUNCH_INITIAL_REFRESH
        else
            InitializeAction.SKIP_INITIAL_REFRESH
    }
}