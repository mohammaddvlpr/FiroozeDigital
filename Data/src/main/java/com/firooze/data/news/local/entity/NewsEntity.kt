package com.firooze.data.news.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_entity")
data class NewsEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "date_and_time") val dateAndTime: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "save_time_millis") val saveTimeMillis: Long
)
