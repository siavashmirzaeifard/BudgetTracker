package com.cyafard.budgettracker.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cyafard.budgettracker.presentation.screen.splash.SplashScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "splash"
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screens.Home.route) {
            Text("Home")
        }

        composable(Screens.Add.route) {
            Text("Add")
        }
    }
}