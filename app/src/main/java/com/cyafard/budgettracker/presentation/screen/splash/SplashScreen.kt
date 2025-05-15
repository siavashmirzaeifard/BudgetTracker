package com.cyafard.budgettracker.presentation.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        visible = true
        viewModel.uiEvent.collectLatest {
            when(it) {
                SplashUiEvent.NavigateToHome -> {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(initialScale = 0.5f) + fadeIn(),
            exit = fadeOut()
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null
            )
        }
    }
}