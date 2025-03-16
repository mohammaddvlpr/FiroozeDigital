package com.firooze.data.news.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firooze.data.news.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsItems(items: List<NewsEntity>)

    @Query("SELECT COUNT(*) FROM news_entity WHERE date_and_time > :dateTime")
    fun getNewsNewerThan(dateTime: String): Flow<Int>

    @Query("SELECT * FROM news_entity ORDER BY date_and_time DESC")
    fun pagingSource(): PagingSource<Int, NewsEntity>

    @Query("DELETE FROM news_entity")
    suspend fun deleteAll()

    @Query("SELECT MAX(save_time_millis) FROM news_entity")
    suspend fun getLastUpdateTimeMillis(): Long

    @Query("SELECT MIN(date_and_time) FROM news_entity")
    suspend fun getOldestUpdatePublishDateTime(): String?

    @Query("SELECT * FROM news_entity WHERE id = :id")
    suspend fun getNewsById(id: String): NewsEntity?

}