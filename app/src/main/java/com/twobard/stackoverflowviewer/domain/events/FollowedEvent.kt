package com.twobard.stackoverflowviewer.domain.events

import androidx.annotation.StringRes
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.domain.user.User

sealed class FollowedEvent(@StringRes val resId: Int, val user: User) {
    class Followed(
        messageRes: Int = R.string.followed,
        user: User
    ) : FollowedEvent(messageRes, user)
    class Unfollowed(
        messageRes: Int = R.string.unfollowed,
        user: User
    ) : FollowedEvent(messageRes, user)
}