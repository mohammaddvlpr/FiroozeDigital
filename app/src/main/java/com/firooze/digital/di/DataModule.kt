package com.firooze.digital.di

import android.content.Context
import com.firooze.data.network.NetworkStatusProvider
import com.firooze.data.network.NetworkStatusProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideNetworkStatusProvider(@ApplicationContext context: Context): NetworkStatusProvider {
        return NetworkStatusProviderImpl(context)
    }

}