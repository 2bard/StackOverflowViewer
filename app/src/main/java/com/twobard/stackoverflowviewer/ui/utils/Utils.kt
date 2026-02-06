package com.twobard.stackoverflowviewer.ui.utils

import com.twobard.stackoverflowviewer.data.dto.BadgeCounts
import com.twobard.stackoverflowviewer.data.dto.StackOverflowUserDto
import kotlin.random.Random


class Utils(){
    companion object {
        fun randomUser(): StackOverflowUserDto {
            return StackOverflowUserDto(
                badge_counts = BadgeCounts(
                    bronze = Random.nextInt(0, 50),
                    silver = Random.nextInt(0, 30),
                    gold = Random.nextInt(0, 10)
                ),
                account_id = Random.nextInt(1, 10000),
                is_employee = Random.nextBoolean(),
                last_modified_date = Random.nextLong(1600000000L, 1700000000L),
                last_access_date = Random.nextLong(1600000000L, 1700000000L),
                reputation_change_year = Random.nextInt(0, 10000),
                reputation_change_quarter = Random.nextInt(0, 2500),
                reputation_change_month = Random.nextInt(0, 1000),
                reputation_change_week = Random.nextInt(0, 500),
                reputation_change_day = Random.nextInt(0, 100),
                reputation = Random.nextInt(1, 100000),
                creation_date = Random.nextLong(1400000000L, 1700000000L),
                user_type = listOf("registered", "unregistered", "moderator").random(),
                user_id = Random.nextInt(1, 10000),
                accept_rate = listOf(Random.nextInt(50, 100), null).random(),
                location = listOf("Earth", "Mars", "Venus", null).random(),
                website_url = listOf("https://example.com", null).random(),
                link = "https://stackoverflow.com/users/${Random.nextInt(1, 10000)}",
                profile_image = "https://example.com/avatar${Random.nextInt(1, 10000)}.png",
                display_name = "User" + Random.nextInt(1, 10000)
            )
        }
    }
}