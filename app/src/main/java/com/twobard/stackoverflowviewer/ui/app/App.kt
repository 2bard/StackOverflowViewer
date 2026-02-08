package com.twobard.stackoverflowviewer.ui.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.list.UsersListScreen
import com.twobard.stackoverflowviewer.ui.viewmodel.UsersListViewModel

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = UsersListDestination.route) {
        composable(UsersListDestination.route) {

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
    }
}

@Composable
fun followMap(resIds: List<Int>): Map<Int, String> =
    resIds.associateWith { stringResource(it) }

object UsersListDestination {
    const val route = "usersList"
}
