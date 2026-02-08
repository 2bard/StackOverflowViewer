package com.twobard.stackoverflowviewer.data.serializer

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.twobard.stackoverflowviewer.domain.state.UsersListSerializer
import com.twobard.stackoverflowviewer.domain.user.User

class UsersListSerializerImp(moshi: Moshi) : UsersListSerializer {

    private val adapter = moshi.adapter<List<User>>(
        Types.newParameterizedType(List::class.java, User::class.java)
    )

    private val followsAdapter = moshi.adapter<List<Int>>(
        Types.newParameterizedType(List::class.java, Integer::class.java)
    )

    override fun serialize(users: List<User>): String =
        adapter.toJson(users)

    override fun deserialize(value: String): List<User> =
        adapter.fromJson(value).orEmpty()

    override fun deserializeFollows(string: String): List<Int>? {
        return followsAdapter.fromJson(string)
    }

    override fun serializeFollows(follows: List<Int>): String {
        return followsAdapter.toJson(follows)
    }
}