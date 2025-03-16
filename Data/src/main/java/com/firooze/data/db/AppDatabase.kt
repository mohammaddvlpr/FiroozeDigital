package com.firooze.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.firooze.data.news.local.NewsDao
import com.firooze.data.news.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}