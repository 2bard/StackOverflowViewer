package com.twobard.stackoverflowviewer.data.dto

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class BadgeCounts(
    val bronze: Int,
    val silver: Int,
    val gold: Int
)

