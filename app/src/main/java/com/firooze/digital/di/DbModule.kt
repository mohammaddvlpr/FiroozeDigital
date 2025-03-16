package com.firooze.digital.di

import android.content.Context
import androidx.room.Room
import com.firooze.data.db.AppDatabase
import com.firooze.data.news.local.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            "firooze_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao {
        return  appDatabase.newsDao()
    }
}