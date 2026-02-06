package com.twobard.stackoverflowviewer.domain.local

import com.twobard.stackoverflowviewer.domain.user.User
import kotlinx.coroutines.flow.Flow

interface LocalDataStoreInterface {
    fun followedUsers(): Flow<List<Int>>
    suspend fun changeFollowStatus(user: User)
}