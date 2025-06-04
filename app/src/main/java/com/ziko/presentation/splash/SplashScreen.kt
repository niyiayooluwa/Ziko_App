package com.ziko.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ziko.R
import com.ziko.navigation.Screen
import com.ziko.presentation.auth.UserViewModel
import com.ziko.util.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull

// Shows the logo, waits 2 seconds, then moves to the Login screen
@Composable
fun SplashScreen(navController: NavController) { // Correct: Only one top-level Composable function
    val splashViewModel: SplashViewModel = viewModel() // Assuming SplashViewModel is not Hilt-injected
    val userViewModel: UserViewModel = hiltViewModel() // Assuming UserViewModel is Hilt-injected

    // Collect states from ViewModels
    val user by userViewModel.user.collectAsState()
    val tokenExpired by userViewModel.tokenExpired.collectAsState()
    val isUserCheckComplete by userViewModel.isUserCheckComplete.collectAsState() // New state from UserViewModel

    // State to track if the initial splash timer has finished
    var splashTimerFinished by remember { mutableStateOf(false) }

    // Step 1: Start the initial splash screen timer
    LaunchedEffect(Unit) {
        splashViewModel.startSplashTimer {
            splashTimerFinished = true
        }
    }

    // Step 2: Once the splash timer is done, start the token/user check
    // This LaunchedEffect will trigger ONLY when splashTimerFinished becomes true
    LaunchedEffect(splashTimerFinished) {
        if (splashTimerFinished) {
            userViewModel.fetchUser() // Start fetching user, which will set isUserCheckComplete eventually
        }
    }

    // Step 3: Navigate based on all conditions being met
    // Navigation will only occur when both the splash timer is finished AND the user check is complete
    LaunchedEffect(splashTimerFinished, isUserCheckComplete, tokenExpired, user) {
        if (splashTimerFinished && isUserCheckComplete) {
            when {
                tokenExpired -> {
                    // Token expired, navigate to Login
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
                user != null -> {
                    // User data successfully fetched, navigate to Home
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
                else -> {
                    // No user data (either no token, or fetch failed and token not expired), navigate to Onboarding
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
        }
    }

    // Your actual splash screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF410FA3)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}
