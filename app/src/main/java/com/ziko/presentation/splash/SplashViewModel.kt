package com.ziko.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {


    // Function to simulate background work (e.g., checking if user is logged in)
    fun startSplashTimer(onTimerFinished: () -> Unit) {
        viewModelScope.launch {
            delay(2000) // Wait 2 seconds
            onTimerFinished() // Call this function when done
        }
    }
}
