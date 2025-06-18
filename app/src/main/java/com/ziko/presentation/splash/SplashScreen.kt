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
import com.ziko.presentation.profile.UserViewModel
import com.ziko.core.util.UpdateSystemBarsColors

/**
 * Splash screen composable that:
 * 1. Displays a splash logo for a fixed duration.
 * 2. Triggers user authentication check after splash.
 * 3. Navigates to the appropriate screen based on token validity and user data presence.
 *
 * This is the first screen shown on app launch.
 *
 * @param navController Used to navigate between screens after splash logic is complete.
 */
@Composable
fun SplashScreen(navController: NavController) {
    // SplashViewModel controls only the splash delay timer
    val splashViewModel: SplashViewModel = viewModel() // No Hilt
    // UserViewModel handles token, user state, and authentication checks
    val userViewModel: UserViewModel = hiltViewModel()

    // State collection from UserViewModel
    val user by userViewModel.user.collectAsState()
    val tokenExpired by userViewModel.tokenExpired.collectAsState()
    val isUserCheckComplete by userViewModel.isUserCheckComplete.collectAsState()

    // Local state for splash screen timing
    var splashTimerFinished by remember { mutableStateOf(false) }

    // Set system bar colors to match splash branding
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color(0xFF410FA3)
    )

    /**
     * STEP 1: Start a timer on first composition.
     * After 2 seconds, splashTimerFinished becomes true.
     */
    LaunchedEffect(Unit) {
        splashViewModel.startSplashTimer {
            splashTimerFinished = true
        }
    }

    /**
     * STEP 2: When the timer finishes, trigger user info fetch.
     * This ensures the splash always shows for 2 seconds *before* any navigation.
     */
    LaunchedEffect(splashTimerFinished) {
        if (splashTimerFinished) {
            userViewModel.fetchUser()
        }
    }

    /**
     * STEP 3: Wait for both splash timer and user fetch to complete.
     * Then navigate based on app state:
     * - Token expired → Login
     * - User loaded → Home
     * - No token or failed fetch → Onboarding
     */
    LaunchedEffect(splashTimerFinished, isUserCheckComplete, tokenExpired, user) {
        if (splashTimerFinished && isUserCheckComplete) {
            when {
                tokenExpired -> {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
                user != null -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
                else -> {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
        }
    }

    /**
     * STEP 4: UI layer — splash logo and background.
     */
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

