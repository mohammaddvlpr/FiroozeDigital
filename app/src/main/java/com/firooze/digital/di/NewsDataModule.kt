package com.firooze.digital.di

import com.firooze.data.news.NewsRepositoryImpl
import com.firooze.data.news.local.NewsLocalDataSource
import com.firooze.data.news.local.NewsLocalDataSourceImpl
import com.firooze.data.news.remote.NewsRemoteDataSource
import com.firooze.data.news.remote.NewsRemoteDataSourceImpl
import com.firooze.data.news.remote.NewsService
import com.firooze.domain.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsDataModule {

    companion object {
        @Provides
        fun provideNewsService(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): NewsService {

            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(NewsService::class.java)
        }

    }

    @Binds
    abstract fun bindNewsLocalDataSource(impl: NewsLocalDataSourceImpl): NewsLocalDataSource

    @Binds
    abstract fun bindNewsRemoteDataSource(impl: NewsRemoteDataSourceImpl): NewsRemoteDataSource

    @Binds
    abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository


}