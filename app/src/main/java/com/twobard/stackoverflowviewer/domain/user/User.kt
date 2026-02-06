package com.twobard.stackoverflowviewer.domain.user

data class User(
    val id: Int,
    val displayName: String,
    val reputation: Int,
    val profileImage: String
)
