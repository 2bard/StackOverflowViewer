package com.twobard.stackoverflowviewer.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twobard.stackoverflowviewer.ui.list.UsersListScreen
import kotlinx.serialization.Serializable

@Serializable
object UsersList

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = UsersList) {
        composable<UsersList> {
            UsersListScreen()
        }
    }
}