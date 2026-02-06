package com.twobard.stackoverflowviewer.domain.repository

import com.twobard.stackoverflowviewer.domain.user.User
import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    fun followedUsers() : Flow<List<Int>>
    suspend fun followUser(user: User)
}