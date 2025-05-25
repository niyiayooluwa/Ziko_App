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
import com.ziko.presentation.assessment.AssessmentCompletionScreen
import com.ziko.presentation.assessment.AssessmentContent
import com.ziko.presentation.assessment.AssessmentViewModel
import com.ziko.presentation.assessment.AssessmentViewModelFactory
import com.ziko.presentation.auth.login.LoginScreen
import com.ziko.presentation.auth.signup.SignUpScreenOne
import com.ziko.presentation.auth.signup.SignUpScreenTwo
import com.ziko.presentation.auth.signup.SignUpViewModel
import com.ziko.presentation.home.AssessmentScreen
import com.ziko.presentation.home.LessonScreen
import com.ziko.presentation.lesson.LessonCompletionScreen
import com.ziko.presentation.lesson.LessonContent
import com.ziko.presentation.lesson.LessonIntroScreen
import com.ziko.presentation.lesson.LessonLoadingScreen
import com.ziko.presentation.lesson.LessonViewModel
import com.ziko.presentation.lesson.LessonViewModelFactory
import com.ziko.presentation.practice.AssessmentLoadingScreen
import com.ziko.presentation.practice.PracticeCompletionScreen
import com.ziko.presentation.practice.PracticeContent
import com.ziko.presentation.practice.PracticeLoadingScreen
import com.ziko.presentation.practice.PracticeViewModel
import com.ziko.presentation.practice.PracticeViewModelFactory
import com.ziko.presentation.splash.OnboardingScreen
import com.ziko.presentation.splash.SplashScreen
import com.ziko.util.getNextLessonId

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

        // Assessment Screen
        composable(Screen.Assessment.route) { AssessmentScreen(navController = navController) }

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
            val lessonViewModel: LessonViewModel = viewModel(
                factory = LessonViewModelFactory(SavedStateHandle(mapOf("lessonId" to lessonId)))
            )
            val isFirstScreen = lessonViewModel.currentIndex.value == 0
            LessonIntroScreen(
                navController, lessonId,
                onCancel = { navController.navigate(Screen.Home.route) },
                onNavigateBack = {
                    // If on first screen, go back to Home, otherwise go to previous screen
                    if (isFirstScreen) {
                        navController.popBackStack(Screen.Home.route, false)
                    } else {
                        lessonViewModel.previousScreen()
                    }
                },
                isFirstScreen = isFirstScreen,
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
            val currentScreen = lessonViewModel.currentIndex.value + 2
            val totalScreens = lessonViewModel.totalScreens + 1
            val isFirstScreen = lessonViewModel.currentIndex.value == 0

            if (content != null) {
                LessonContent(
                    content = content,
                    progress = progress,
                    currentScreen = currentScreen,
                    totalScreens = totalScreens,
                    onCancel = { navController.popBackStack(Screen.Home.route, false) },
                    onContinue = {
                        lessonViewModel.nextScreen {
                            navController.navigate(Screen.LessonCompletion(lessonId).route) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                    },
                    onNavigateBack = {
                        // If on first screen, go back to Home, otherwise go to previous screen
                        if (isFirstScreen) {
                            navController.popBackStack(Screen.Home.route, false)
                        } else {
                            lessonViewModel.previousScreen()
                        }
                    },
                    isFirstScreen = isFirstScreen,
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

        // Lesson Completion
        composable(
            Screen.LessonCompletion.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            LessonCompletionScreen(
                onPopBackStack = {navController.popBackStack()},
                onContinuePractice = { navController.navigate(Screen.PracticeLoading(lessonId).route) },
                onBackToHome = { navController.popBackStack(Screen.Home.route, inclusive = false) },
                lessonId = lessonId
            )
        }
        
        //Practice Loading
        composable(
            Screen.PracticeLoading.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            PracticeLoadingScreen(
                lessonId = lessonId,
                onProgress = {
                    navController.navigate(Screen.PracticeContent(lessonId).route)
                }
            )
        }
        
        //Practice Content
        composable(
            Screen.PracticeContent.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
            ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val viewModel: PracticeViewModel = viewModel(
                factory = PracticeViewModelFactory(SavedStateHandle(mapOf("lessonId" to lessonId)))
            )
            val content = viewModel.currentScreen
            val progress = viewModel.progress
            val currentScreen = viewModel.currentIndex.value
            val totalScreens = viewModel.totalScreens
            val isFirstScreen = viewModel.currentIndex.value == 0
            if (content != null) {
                PracticeContent(
                    content = content,
                    progress = progress,
                    currentScreen = currentScreen,
                    totalScreens = totalScreens,
                    isFirstScreen = isFirstScreen,
                    onNavigateBack = {
                        if (isFirstScreen) {
                            navController.popBackStack(Screen.Home.route, false)
                        } else {
                            viewModel.previousScreen()
                        }
                    },
                    onCancel = {
                        navController.popBackStack(Screen.Home.route, false)
                    },
                    onContinue = {
                        viewModel.nextScreen {
                            navController.navigate(Screen.LessonCompletion(lessonId).route) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Practice Completion
        composable(
            Screen.PracticeCompletion.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val nextLessonId = getNextLessonId(lessonId)
            PracticeCompletionScreen(
                onPopBackStack = {navController.popBackStack()},
                onContinueLesson = { navController.navigate(Screen.LessonLoading(nextLessonId).route) },
                onBackToHome = { navController.popBackStack(Screen.Home.route, inclusive = false) },
                lessonId = lessonId
            )
        }

        //Assessment Loading
        composable(
            Screen.AssessmentLoading.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            AssessmentLoadingScreen(
                lessonId = lessonId,
                onProgress = {
                    navController.navigate(Screen.AssessmentContent(lessonId).route)
                }
            )
        }

        //Assessment Content
        composable(
            Screen.PracticeContent.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val viewModel: AssessmentViewModel = viewModel(
                factory = AssessmentViewModelFactory(SavedStateHandle(mapOf("lessonId" to lessonId)))
            )
            val content = viewModel.currentScreen
            val progress = viewModel.progress
            val currentScreen = viewModel.currentIndex.value
            val totalScreens = viewModel.totalScreens
            val isFirstScreen = viewModel.currentIndex.value == 0
            if (content != null) {
                AssessmentContent(
                    content = content,
                    progress = progress,
                    currentScreen = currentScreen,
                    totalScreens = totalScreens,
                    isFirstScreen = isFirstScreen,
                    onNavigateBack = {
                        if (isFirstScreen) {
                            navController.popBackStack(Screen.Home.route, false)
                        } else {
                            viewModel.previousScreen()
                        }
                    },
                    onCancel = {
                        navController.popBackStack(Screen.Home.route, false)
                    },
                    onContinue = {
                        viewModel.nextScreen {
                            navController.navigate(Screen.LessonCompletion(lessonId).route) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Assessment Completion
        composable(
            Screen.AssessmentCompletion.BASE_ROUTE,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val nextLessonId = getNextLessonId(lessonId)
            AssessmentCompletionScreen(
                onPopBackStack = {navController.popBackStack()},
                onContinueLesson = { navController.navigate(Screen.AssessmentLoading(lessonId).route) },
                onBackToHome = { navController.popBackStack(Screen.Home.route, inclusive = false) },
                lessonId = lessonId
            )
        }
    }
}
