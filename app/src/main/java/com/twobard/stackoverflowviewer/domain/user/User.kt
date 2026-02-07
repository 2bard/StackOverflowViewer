package com.twobard.stackoverflowviewer.domain.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val displayName: String,
    val reputation: Int,
    val profileImage: String
)
