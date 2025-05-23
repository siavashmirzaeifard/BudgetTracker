package com.cyafard.budgettracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cyafard.budgettracker.presentation.screen.add.AddScreen
import com.cyafard.budgettracker.presentation.screen.home.HomeScreen
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
            SplashScreen(modifier = modifier, navController = navController)
        }

        composable(Screens.Home.route) {
            HomeScreen(modifier = modifier, navController = navController)
        }

        composable(Screens.Add.route) {
            AddScreen(modifier = modifier, navController = navController)
        }
    }
}