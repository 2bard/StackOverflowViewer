package com.twobard.stackoverflowviewer.data.repository

import androidx.annotation.StringRes
import com.twobard.stackoverflowviewer.data.api.Order
import com.twobard.stackoverflowviewer.data.api.Site
import com.twobard.stackoverflowviewer.data.api.Sort
import com.twobard.stackoverflowviewer.data.api.StackOverflowApiImpl
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.data.dto.toUser
import com.twobard.stackoverflowviewer.domain.repository.UserRepository
import com.twobard.stackoverflowviewer.domain.user.User
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(val stackOverflowApiImpl: StackOverflowApiImpl) :
    UserRepository {


    override suspend fun getUsers(
        page: Int,
        pageSize: Int,
        order: Order,
        sort: Sort,
        site: Site
    ): Result<List<User>> {
        return try {

            val dtos = stackOverflowApiImpl.getUsers(
                page,
                pageSize,
                order.value,
                sort.value,
                site.value
            )

            val result = dtos.items.map { it.toUser() }

            Result.success(result)
        } catch (e: UnknownHostException) {
            Result.failure(NetworkError.NoInternet(e))
        } catch (e: IOException) {
            Result.failure(NetworkError.NetworkFailure(e))
        } catch (e: Exception) {
            Result.failure(NetworkError.UnknownError(e))
        }
    }


    sealed class NetworkError(@StringRes val messageRes: Int, cause: Throwable? = null) : Throwable(null, cause) {
        class NoInternet(cause: Throwable? = null) : NetworkError(R.string.no_internet, cause)
        class NetworkFailure(cause: Throwable? = null) : NetworkError(R.string.network_error, cause)
        class UnknownError(cause: Throwable? = null) : NetworkError(R.string.unknown_error, cause)
    }
}