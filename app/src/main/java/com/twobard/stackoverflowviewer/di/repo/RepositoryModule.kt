package com.twobard.stackoverflowviewer.di.repo

import com.twobard.stackoverflowviewer.data.repository.FollowRepositoryImpl
import com.twobard.stackoverflowviewer.data.repository.UserRepositoryImpl
import com.twobard.stackoverflowviewer.domain.repository.FollowRepository
import com.twobard.stackoverflowviewer.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindFollowRepository(
        impl: FollowRepositoryImpl
    ): FollowRepository
}