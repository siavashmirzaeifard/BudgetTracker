package com.cyafard.budgettracker.presentation.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000, easing = EaseInBounce)
    )

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
                contentDescription = null,
                modifier = modifier.size(120.dp).scale(scale)
            )
        }
    }
}