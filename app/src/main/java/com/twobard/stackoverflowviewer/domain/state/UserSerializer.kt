package com.twobard.stackoverflowviewer.domain.state

import com.twobard.stackoverflowviewer.domain.user.User

interface UsersListSerializer {
    fun serialize(users: List<User>): String
    fun deserialize(value: String): List<User>

    //this should maybe be a separate interface (interface seg principle)
    fun deserializeFollows(string: String): List<Int>?
    fun serializeFollows(follows: List<Int>): String
}