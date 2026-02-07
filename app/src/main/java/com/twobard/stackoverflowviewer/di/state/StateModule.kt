package com.twobard.stackoverflowviewer.di.state

import com.squareup.moshi.Moshi
import com.twobard.stackoverflowviewer.data.serializer.UsersListSerializerImp
import com.twobard.stackoverflowviewer.domain.state.UsersListSerializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StateModule {

    @Provides
    fun provideUsersStateSerializer(
        moshi: Moshi
    ): UsersListSerializer =
        UsersListSerializerImp(moshi)

}