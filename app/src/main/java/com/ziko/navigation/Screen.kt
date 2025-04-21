package com.ziko.navigation

//All routes in one sealed class
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Onboarding : Screen("onboard")
    data object SignupOne : Screen("signupOne")
    data object SignupTwo : Screen("signupTwo")
}