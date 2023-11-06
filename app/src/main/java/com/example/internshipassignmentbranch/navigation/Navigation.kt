package com.example.internshipassignmentbranch.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.internshipassignmentbranch.ui.screens.LoginScreen
import com.example.internshipassignmentbranch.ui.screens.MessagesScreen
import com.example.internshipassignmentbranch.ui.screens.ReplyScreen
import com.example.internshipassignmentbranch.ui.viewmodel.SharedViewModel
import com.example.internshipassignmentbranch.utils.TokenManager


@Composable
fun Navigation(tokenManager: TokenManager) {

    val navController: NavHostController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    val startDestination = if (tokenManager.getToken() != null) {
        Destinations.Home.route // Screen to navigate when the token is not null
    } else {
        Destinations.LoginScreen.route // Screen to navigate when the token is null
    }
    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(Destinations.Home.route) {
            MessagesScreen(
                onClick = {
                    navController.navigate(Destinations.MessageReplyScreen.route) {

                        popUpTo(Destinations.Home.route) {
                            inclusive = true
                        }
                    }
                }, sharedViewModel, navController
            )
        }
        composable(Destinations.LoginScreen.route) {
            LoginScreen(
                onClick = {
                    // Navigate to Home after successful login
                    navController.navigate(Destinations.Home.route) {}
                }, tokenManager = tokenManager // Pass the tokenManager object
            )
        }
        composable(Destinations.MessageReplyScreen.route) {
            ReplyScreen(sharedViewModel, onBackPressed = {
                navController.navigate(Destinations.Home.route)
            })
        }
        composable(Destinations.NotFound.route) {
            //put your Not found screen here
        }
    }

}


