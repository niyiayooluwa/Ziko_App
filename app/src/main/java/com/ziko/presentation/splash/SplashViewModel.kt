package com.ziko.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for the Splash screen.
 * Delays for 2 seconds before triggering navigation.
 */
class SplashViewModel : ViewModel() {
    /**
     * Simulates a splash screen delay, then calls [onTimerFinished].
     */
    fun startSplashTimer(onTimerFinished: () -> Unit) {
        viewModelScope.launch {
            delay(2000)
            onTimerFinished()
        }
    }
}

