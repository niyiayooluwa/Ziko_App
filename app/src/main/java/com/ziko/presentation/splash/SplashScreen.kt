package com.ziko.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ziko.R
import com.ziko.navigation.Screen

// Shows the logo, waits 2 seconds, then moves to the Login screen
@Composable
fun SplashScreen(navController: NavController) {
    // Getting the ViewModel
    val splashViewModel: SplashViewModel = viewModel()
    // Call the ViewModel function to start the timer
    splashViewModel.startSplashTimer {
        // Once the timer is done, navigate to the Login screen
        navController.navigate(Screen.Onboarding.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    // UI part - shows the logo in the center
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF410FA3)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}
