package com.twobard.stackoverflowviewer.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.domain.user.User

@Composable
fun RowScope.FollowButton(onClickFollow: (User) -> Unit, userObject: User, isFollowed: Boolean){

    Button(
        colors = ButtonDefaults.buttonColors().copy(containerColor = if(isFollowed) MaterialTheme.colorScheme.onPrimaryFixed else MaterialTheme.colorScheme.onPrimaryFixedVariant),
        onClick = {
            onClickFollow.invoke(userObject)
        }){
        Icon(
            contentDescription = if(isFollowed) stringResource(R.string.unfollow) else stringResource(
                R.string.follow
            ),
            imageVector = if(isFollowed) Icons.Default.PersonRemove else Icons.Default.PersonAdd
        )
    }

}