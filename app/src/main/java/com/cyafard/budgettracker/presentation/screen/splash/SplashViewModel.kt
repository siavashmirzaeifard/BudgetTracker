package com.cyafard.budgettracker.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {
    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)
            _uiEvent.emit(SplashUiEvent.NavigateToHome)
        }
    }
}

sealed class SplashUiEvent {
    object NavigateToHome: SplashUiEvent()
}