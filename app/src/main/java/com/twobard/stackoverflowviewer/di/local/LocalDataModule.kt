package com.twobard.stackoverflowviewer.di.local

import android.content.Context
import com.squareup.moshi.Moshi
import com.twobard.stackoverflowviewer.data.local.LocalDataStore
import com.twobard.stackoverflowviewer.domain.local.LocalDataStoreInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Context) : LocalDataStoreInterface {
        return LocalDataStore(context)
    }
}