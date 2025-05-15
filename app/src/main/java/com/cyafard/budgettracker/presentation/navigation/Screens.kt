package com.cyafard.budgettracker.presentation.navigation

sealed class Screens(val route: String) {
    object Splash: Screens(route = "splash")
    object Home: Screens(route = "home")
    object Add: Screens(route = "add")
}