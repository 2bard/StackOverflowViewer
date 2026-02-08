package com.twobard.stackoverflowviewer.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.data.dto.toUser
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.components.EmptyListState
import com.twobard.stackoverflowviewer.ui.components.LoadingState
import com.twobard.stackoverflowviewer.ui.components.UserCard
import com.twobard.stackoverflowviewer.ui.theme.paddingMedium
import com.twobard.stackoverflowviewer.ui.theme.paddingSmall
import com.twobard.stackoverflowviewer.ui.utils.Utils.Companion.randomUser
import com.twobard.stackoverflowviewer.ui.utils.followMap
import com.twobard.stackoverflowviewer.ui.viewmodel.UsersListViewModel


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

@Composable
fun UsersListScreenState(){
    val snackbarHostState = remember { SnackbarHostState() }

    //UI state
    val viewModel = hiltViewModel<UsersListViewModel>()
    val usersItems by viewModel.usersWithFollowStatus.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    //Setup strings here as they can't be retrieved in launchedeffect and using LocalContext.current is incorrect
    val followedEvent = followMap(listOf(R.string.followed, R.string.unfollowed))
    val errorMap = followMap(listOf(R.string.unknown_error, R.string.network_error, R.string.no_internet))
    val unknownError = stringResource(R.string.unknown_error)

    //click listners
    val onRefresh = {
        viewModel.refresh()
    }

    val onClickFollow: (User) -> (Unit) = {
        viewModel.changeFollowStatus(it)
    }

    //Collect follow events for snackbar
    LaunchedEffect(Unit) {
        viewModel.followedEvent.collect { event ->
            followedEvent[event.resId]?.let {
                snackbarHostState.showSnackbar(message = it + " " + event.user.displayName)
            }
        }
    }

    //Collect error events for snackbar
    LaunchedEffect(Unit) {
        viewModel.errors.collect { error ->
            snackbarHostState.showSnackbar(message = errorMap[error?.messageRes] ?: unknownError)
        }
    }

    UsersListScreen(snackbarHostState, isLoading, usersItems, onRefresh, onClickFollow)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    isLoading: Boolean,
    @PreviewParameter(UserListPreviewProvider::class) users: List<Pair<User, Boolean>>,
    onRefresh: () -> Unit = {},
    onClickFollow : (User) -> Unit = {}
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


    Box(modifier = Modifier.padding(paddingMedium), contentAlignment = Alignment.Center) {

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









