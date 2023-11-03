package com.example.internshipassignmentbranch.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object LoginScreen : Destinations("login")
    object QueryScreen : Destinations("query_screen")
    object NotFound : Destinations("notFound")

    // Helper function to construct the route with parameters
    fun createRoute(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
