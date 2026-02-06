package com.twobard.stackoverflowviewer.ui.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.list.UsersListScreen
import com.twobard.stackoverflowviewer.ui.viewmodel.UsersListViewModel

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = UsersListDestination.route) {
        composable(UsersListDestination.route) {

            val snackbarHostState = remember { SnackbarHostState() }
            val viewModel = hiltViewModel<UsersListViewModel>()
            val usersItems = viewModel.users.collectAsState().value
            val onRefresh = {

            }

            val onClickFollow: (User) -> (Unit) = {

            }
            UsersListScreen(snackbarHostState, usersItems, onRefresh, onClickFollow)
        }
    }
}

object UsersListDestination {
    const val route = "usersList"
}
