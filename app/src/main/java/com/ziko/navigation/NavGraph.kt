package com.ziko.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ziko.presentation.auth.login.LoginScreen
import com.ziko.presentation.auth.signup.SignUpScreenOne
import com.ziko.presentation.auth.signup.SignUpScreenTwo
import com.ziko.presentation.auth.signup.SignUpViewModel
import com.ziko.presentation.home.LessonScreen
import com.ziko.presentation.lesson.LessonCompletionScreen
import com.ziko.presentation.lesson.LessonContent
import com.ziko.presentation.lesson.LessonIntroScreen
import com.ziko.presentation.lesson.LessonLoadingScreen
import com.ziko.presentation.lesson.LessonViewModel
import com.ziko.presentation.lesson.LessonViewModelFactory
import com.ziko.presentation.splash.OnboardingScreen
import com.ziko.presentation.splash.SplashScreen
@Composable
fun NavGraph(
    navController: NavHostController
) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) { SplashScreen(navController = navController) }

        // Onboarding Screen
        composable(Screen.Onboarding.route) { OnboardingScreen(navController = navController) }

        // Login Screen
        composable(Screen.Login.route) { LoginScreen(navController = navController) }

        // Sign Up Screen 1
        composable(Screen.SignOne.route) {
            // Passing the ViewModel to SignUpScreenOne
            SignUpScreenOne(navController = navController, viewModel = signUpViewModel)
        }

        // Sign Up Screen 2
        composable(Screen.SignTwo.route) {
            // Passing the ViewModel to SignUpScreenTwo
            SignUpScreenTwo(navController = navController, viewModel = signUpViewModel)
        }

        // Home Screen
        composable(Screen.Home.route) { LessonScreen(navController = navController) }

        // Lesson Loading
        composable(
            Screen.LessonLoading.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            LessonLoadingScreen(navController, lessonId)
        }

        // Intro Screen
        composable(
            Screen.LessonIntro.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            LessonIntroScreen(
                navController, lessonId,
                onCancel = { navController.navigate(Screen.Home.route) }
            )
        }

        // Lesson Screen
        composable(
            Screen.LessonContent.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!

            // Create the ViewModel scoped to this screen with the lessonId
            val lessonViewModel: LessonViewModel = viewModel(
                factory = LessonViewModelFactory(SavedStateHandle(mapOf("lessonId" to lessonId)))
            )

            val content = lessonViewModel.currentScreen
            val progress = lessonViewModel.progress

            if (content != null) {
                LessonContent(
                    content = content,
                    progress = progress,
                    onCancel = { navController.popBackStack(Screen.Home.route, false) },
                    onContinue = {
                        lessonViewModel.nextScreen {
                            navController.navigate(Screen.LessonCompletion(lessonId).route) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                    }
                )
            } else {
                // Handle null content case
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Completion
        composable(
            Screen.LessonCompletion.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            LessonCompletionScreen(
                onContinuePractice = { /* nav to practice later */ },
                onBackToHome = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                },
                lessonId = lessonId
            )
        }
    }
}
