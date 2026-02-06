package com.twobard.stackoverflowviewer.data.dto

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class StackOverflowUser(
    val badge_counts: BadgeCounts,
    val account_id: Int,
    val is_employee: Boolean,
    val last_modified_date: Long,
    val last_access_date: Long,
    val reputation_change_year: Int,
    val reputation_change_quarter: Int,
    val reputation_change_month: Int,
    val reputation_change_week: Int,
    val reputation_change_day: Int,
    val reputation: Int,
    val creation_date: Long,
    val user_type: String,
    val user_id: Int,
    val accept_rate: Int?,
    val location: String?,
    val website_url: String?,
    val link: String,
    val profile_image: String,
    val display_name: String
)