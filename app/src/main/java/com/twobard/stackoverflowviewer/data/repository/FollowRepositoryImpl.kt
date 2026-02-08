package com.twobard.stackoverflowviewer.data.repository

import com.twobard.stackoverflowviewer.domain.local.LocalDataStoreInterface
import com.twobard.stackoverflowviewer.domain.repository.FollowRepository
import com.twobard.stackoverflowviewer.domain.user.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(private val localDataStore: LocalDataStoreInterface) : FollowRepository {
    override fun followedUsers(): Flow<List<Int>> = localDataStore.followedUsers()
    override suspend fun changeFollowState(user: User) = localDataStore.changeFollowStatus(user)
}