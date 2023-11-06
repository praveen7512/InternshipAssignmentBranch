package com.example.internshipassignmentbranch.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object LoginScreen : Destinations("login")
    object MessageReplyScreen : Destinations("query_screen")
    object NotFound : Destinations("notFound")

}
