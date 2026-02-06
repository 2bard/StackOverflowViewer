package com.twobard.stackoverflowviewer

import com.twobard.stackoverflowviewer.data.dto.BadgeCounts
import com.twobard.stackoverflowviewer.data.dto.StackOverflowUserDto
import kotlin.random.Random

class Utils {

}

class StackOverflowUserDtoBuilder {
    var badge_counts: BadgeCounts = BadgeCounts(1, 2, 3)
    var account_id: Int = 123
    var is_employee: Boolean = false
    var last_modified_date: Long = 1620000000L
    var last_access_date: Long = 1620000000L
    var reputation_change_year: Int = 100
    var reputation_change_quarter: Int = 25
    var reputation_change_month: Int = 10
    var reputation_change_week: Int = 5
    var reputation_change_day: Int = 1
    var reputation: Int = 1000
    var creation_date: Long = 1500000000L
    var user_type: String = "registered"
    var user_id: Int = 456
    var accept_rate: Int? = 80
    var location: String? = "Earth"
    var website_url: String? = "https://example.com"
    var link: String = "https://stackoverflow.com/users/456"
    var profile_image: String = "https://example.com/avatar.png"
    var display_name: String = "Test User"

    fun build() = StackOverflowUserDto(
        badge_counts,
        account_id,
        is_employee,
        last_modified_date,
        last_access_date,
        reputation_change_year,
        reputation_change_quarter,
        reputation_change_month,
        reputation_change_week,
        reputation_change_day,
        reputation,
        creation_date,
        user_type,
        user_id,
        accept_rate,
        location,
        website_url,
        link,
        profile_image,
        display_name
    )
}
