package com.twobard.stackoverflowviewer.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twobard.stackoverflowviewer.ui.list.UsersListScreenState

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = UsersListDestination.route) {
        composable(UsersListDestination.route) {
            UsersListScreenState()
        }
    }
}



object UsersListDestination {
    const val route = "usersList"
}
