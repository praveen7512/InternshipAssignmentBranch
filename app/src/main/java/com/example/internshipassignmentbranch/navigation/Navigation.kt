package com.example.internshipassignmentbranch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route
    ) {
        composable(Destinations.Home.route) {

        }

        composable(Destinations.LoginScreen.route) {

        }
        composable(Destinations.QueryScreen.route) {

        }
        composable(Destinations.NotFound.route) {

        }
    }

}