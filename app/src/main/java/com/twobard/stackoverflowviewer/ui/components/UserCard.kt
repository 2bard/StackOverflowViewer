package com.twobard.stackoverflowviewer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.list.FollowButton
import com.twobard.stackoverflowviewer.ui.list.ProfilePicture
import com.twobard.stackoverflowviewer.ui.list.UserPreviewProvider
import com.twobard.stackoverflowviewer.ui.theme.displayPictureSize
import com.twobard.stackoverflowviewer.ui.theme.listElevation
import com.twobard.stackoverflowviewer.ui.theme.paddingMedium
import com.twobard.stackoverflowviewer.ui.theme.paddingSmall

@Preview
@Composable
fun UserCard(@PreviewParameter(UserPreviewProvider::class) user: Pair<User, Boolean>, onClickFollow: (User) -> Unit = {}) {

    val userObject = user.first
    val isFollowed = user.second

    Card(elevation = CardDefaults.cardElevation(listElevation)) {
        Row(modifier = Modifier.padding(paddingMedium), verticalAlignment = Alignment.CenterVertically) {

            ProfilePicture(userObject, displayPictureSize)

            Spacer(modifier = Modifier.width(paddingSmall))

            Column(modifier = Modifier.weight(1f)) {

                Row {
                    Text(
                        userObject.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        contentDescription = stringResource(R.string.reputation),
                        imageVector = Icons.Default.Star,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(userObject.reputation.toString(), style = MaterialTheme.typography.bodySmall)
                }

            }

            FollowButton(
                onClickFollow = onClickFollow,
                userObject = userObject,
                isFollowed = isFollowed
            )
        }
    }
}