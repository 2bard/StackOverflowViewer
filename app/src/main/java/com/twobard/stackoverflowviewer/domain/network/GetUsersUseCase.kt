package com.twobard.stackoverflowviewer.domain.network

import com.twobard.stackoverflowviewer.data.local.LocalDataStore
import com.twobard.stackoverflowviewer.domain.repository.UserRepository
import com.twobard.stackoverflowviewer.domain.user.User
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository,
) {

    suspend operator fun invoke(): Result<List<User>?> {
        return repository.getUsers()
    }
}