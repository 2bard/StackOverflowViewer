package com.twobard.stackoverflowviewer.data.dto

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class StackOverflowUsersResponse(
    val items: List<StackOverflowUserDto>,
    val has_more: Boolean,
    val quota_max: Int,
    val quota_remaining: Int
)