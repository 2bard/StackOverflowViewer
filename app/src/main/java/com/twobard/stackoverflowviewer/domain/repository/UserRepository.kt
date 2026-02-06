package com.twobard.stackoverflowviewer.domain.repository

import com.twobard.stackoverflowviewer.data.api.Order
import com.twobard.stackoverflowviewer.data.api.Site
import com.twobard.stackoverflowviewer.data.api.Sort
import com.twobard.stackoverflowviewer.domain.user.User

interface UserRepository {
    suspend fun getUsers(page: Int = 1,
                         pageSize: Int = 20,
                         order: Order = Order.DESC,
                         sort: Sort = Sort.REPUTATION,
                         site: Site = Site.STACK_OVERFLOW): Result<List<User>>
}