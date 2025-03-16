package com.firooze.digital.di

import com.firooze.digital.provider.dateTime.DateTimeProvider
import com.firooze.digital.provider.dateTime.DateTimeProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsUiModule {

    @Binds
    abstract fun bindDateTimeProvider(impl: DateTimeProviderImpl): DateTimeProvider

}