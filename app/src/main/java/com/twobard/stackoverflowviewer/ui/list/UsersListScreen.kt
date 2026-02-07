package com.twobard.stackoverflowviewer.ui.list

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.data.dto.toUser
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.components.LoadingState
import com.twobard.stackoverflowviewer.ui.theme.displayPictureSize
import com.twobard.stackoverflowviewer.ui.theme.paddingMedium
import com.twobard.stackoverflowviewer.ui.theme.paddingSmall
import com.twobard.stackoverflowviewer.ui.utils.Utils.Companion.randomUser


class UserListPreviewProvider : PreviewParameterProvider<List<Pair<User,Boolean>>> {
    override val values = sequenceOf(
        emptyList(),
        List(20) { Pair(randomUser().toUser(), false) },
        List(20) { Pair(randomUser().toUser(), true) }
    )
}

class UserPreviewProvider : PreviewParameterProvider<Pair<User, Boolean>> {
    override val values = sequenceOf(
        Pair(randomUser().toUser(), false),
        Pair(randomUser().toUser(), true)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    snackbarHostState: SnackbarHostState,
    isLoading: Boolean,
    users: List<Pair<User, Boolean>>,
    onRefresh: () -> Unit,
    onClickFollow : (User) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.users))
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            UsersList(
                isLoading = isLoading,
                users = users,
                onClickReload = onRefresh,
                onClickFollow = onClickFollow
            )
        }
    }
}

@Preview
@Composable
fun UsersListLoadingPreview(){
    UsersList(listOf(), true)
}

@Preview
@Composable
fun UsersList(@PreviewParameter(UserListPreviewProvider::class) users: List<Pair<User, Boolean>>,
              isLoading: Boolean = false,
              onClickReload: () -> Unit = {},
              onClickFollow: (User) -> Unit = {}) {


    Box(contentAlignment = Alignment.Center) {

        if(isLoading) {
            LoadingState()
        } else if(users.isEmpty()){
            EmptyListState(onClickReload)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(paddingMedium)
            ) {
                items(
                    key = { users[it].first.id },
                    count = users.size
                ) {
                    UserCard(users[it], onClickFollow)
                }
            }
        }
    }
}

@Composable
fun BoxScope.EmptyListState(onClickReload: () -> Unit = {}) {
    Card {
        Box {
            Column(modifier = Modifier
                .padding(paddingMedium)
                .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.no_users_loaded), style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(paddingSmall))

                Button(onClick = {
                    onClickReload()
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.refresh),
                        )

                        Spacer(modifier = Modifier.width(paddingSmall))

                        Text(stringResource(R.string.refresh), style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UserCard(@PreviewParameter(UserPreviewProvider::class) user: Pair<User, Boolean>, onClickFollow: (User) -> Unit = {}) {

    val userObject = user.first
    val isFollowed = user.second

    Card(elevation = CardDefaults.cardElevation()) {
        Row(modifier = Modifier.padding(paddingMedium)) {

            AsyncImage(
                modifier = Modifier.size(displayPictureSize),
                model = userObject.profileImage,
                contentDescription = null,
            )

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
                    Text(text = stringResource(R.string.reputation), style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(8.dp))
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