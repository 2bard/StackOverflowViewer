package com.twobard.stackoverflowviewer.domain.network

import com.twobard.stackoverflowviewer.domain.events.FollowedEvent
import com.twobard.stackoverflowviewer.domain.repository.FollowRepository
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.viewmodel.UsersListViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowsUseCase @Inject constructor(
    private val repository: FollowRepository,
) {

    operator fun invoke(): Flow<List<Int>> {
        return repository.followedUsers()
    }

    suspend fun changeFollow(user: User) : FollowedEvent? {
        return repository.changeFollowState(user)
    }
}