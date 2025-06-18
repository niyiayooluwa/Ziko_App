package com.ziko.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.ziko.presentation.assessment.AssessmentCompletionViewModel
import com.ziko.presentation.assessment.AssessmentContent
import com.ziko.presentation.assessment.AssessmentLoadingScreen
import com.ziko.presentation.assessment.AssessmentViewModel
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
import com.ziko.presentation.lesson.PreLoadingScreen
import com.ziko.presentation.practice.PracticeCompletionScreen
import com.ziko.presentation.practice.PracticeContent
import com.ziko.presentation.practice.PracticeLoadingScreen
import com.ziko.presentation.practice.PracticeViewModel
import com.ziko.presentation.practice.PracticeViewModelFactory
import com.ziko.presentation.profile.ProfileScreen
import com.ziko.presentation.profile.SecurityScreen
import com.ziko.presentation.profile.UserViewModel
import com.ziko.presentation.splash.OnboardingScreen
import com.ziko.presentation.splash.SplashScreen
import com.ziko.core.util.getNextLessonId

/**
 * NavGraph.kt
 *
 * This file defines the root navigation graph for the application using Jetpack Compose Navigation.
 * It configures routes between screens including onboarding, authentication, lessons, practices, assessments,
 * profile management, and loading/completion screens.
 *
 * This NavGraph manages screen transitions, shares ViewModels where required (e.g., lessonId scoped),
 * and handles navigation arguments via navArguments. It encapsulates the user journey across the entire app.
 *
 * Side-effect handlers such as LaunchedEffect and shared ViewModels are used for controlled flows.
 *
 * @see NavHost
 * @see composable
 * @see Screen
 */


/**
 * Root navigation graph composable that configures the navigation structure of the application.
 *
 * This function connects all major app screens: onboarding, login, registration, lesson modules,
 * practice and assessment modules, including loading and completion states.
 * It supports deep linking via dynamic lessonId routes and leverages ViewModel factories
 * for scoped lesson-specific state management.
 *
 * State hoisting and shared ViewModels (e.g., SignUpViewModel, UserViewModel) are used where
 * inter-screen state persistence is required.
 *
 * @param navController The navigation controller that manages app route transitions
 * @param innerPadding Padding applied from a scaffold or parent container
 * @param modifier Modifier to customize layout or behavior of the NavHost container
 *
 * @throws IllegalStateException if lessonId navigation argument is null
 * @see NavHost
 * @see Screen
 * @see SignUpViewModel
 * @see UserViewModel
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier.padding(innerPadding)
) {
    // Shared ViewModels scoped at the navigation graph level
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        // Splash Screen — first screen shown during app start
        composable(Screen.Splash.route) { SplashScreen(navController = navController) }

        // Onboarding flow after splash
        composable(Screen.Onboarding.route) { OnboardingScreen(navController = navController) }

        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, userViewModel)
        }

        // Sign-up step one (with shared SignUpViewModel)
        composable(Screen.SignOne.route) {
            SignUpScreenOne(navController = navController, viewModel = signUpViewModel)
        }

        // Sign-up step two (collecting user data)
        composable(Screen.SignTwo.route) {
            SignUpScreenTwo(navController = navController, viewModel = signUpViewModel, userViewModel = userViewModel)
        }

        // Lesson hub / dashboard
        composable(Screen.Home.route) {
            LessonScreen(navController, userViewModel)
        }

        // Assessment overview screen
        composable(Screen.Assessment.route) {
            AssessmentScreen(navController, userViewModel)
        }

        // User profile screen
        composable(Screen.Profile.route) {
            ProfileScreen(navController, userViewModel)
        }

        // Account security settings
        composable(Screen.SecurityScreen.route) {
            SecurityScreen(navController, userViewModel)
        }

        // Pre-lesson loading state
        composable(Screen.PreLessonLoading.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            PreLoadingScreen(navController, lessonId)
        }

        // Lesson pre-content loading screen
        composable(Screen.LessonLoading.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            LessonLoadingScreen(navController, lessonId)
        }

        // Lesson introduction with cancellation and navigation callbacks
        composable(Screen.LessonIntro.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val lessonViewModel: LessonViewModel = viewModel(
                factory = LessonViewModelFactory(SavedStateHandle(mapOf("lessonId" to lessonId)))
            )
            val isFirstScreen = lessonViewModel.currentIndex.value == 0

            LessonIntroScreen(
                navController = navController,
                lessonId = lessonId,
                onCancel = { navController.navigate(Screen.Home.route) },
                onNavigateBack = {
                    if (isFirstScreen) navController.popBackStack(Screen.Home.route, false)
                    else lessonViewModel.previousScreen()
                },
                isFirstScreen = isFirstScreen,
            )
        }

        // Lesson content player screen
        composable(Screen.LessonContent.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
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
                    isFirstScreen = isFirstScreen,
                    onCancel = { navController.popBackStack(Screen.Home.route, false) },
                    onContinue = {
                        lessonViewModel.nextScreen {
                            navController.navigate(Screen.LessonCompletion(lessonId).route) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                    },
                    onNavigateBack = {
                        if (isFirstScreen) {
                            navController.popBackStack(Screen.LessonIntro(lessonId).route, false)
                        } else {
                            lessonViewModel.previousScreen()
                        }
                    }
                )
            } else {
                // Fallback loading UI
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        // Lesson completion screen
        composable(Screen.LessonCompletion.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            LessonCompletionScreen(
                onPopBackStack = { navController.popBackStack() },
                onContinuePractice = { navController.navigate(Screen.PracticeLoading(lessonId).route) },
                onBackToHome = { navController.popBackStack(Screen.Home.route, inclusive = false) },
                lessonId = lessonId
            )
        }

        // Practice loading transition
        composable(Screen.PracticeLoading.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            PracticeLoadingScreen(
                lessonId = lessonId,
                onProgress = { navController.navigate(Screen.PracticeContent(lessonId).route) }
            )
        }

        // Practice content screen
        composable(Screen.PracticeContent.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val viewModel: PracticeViewModel = viewModel(
                factory = PracticeViewModelFactory(SavedStateHandle(mapOf("lessonId" to lessonId)))
            )
            val content = viewModel.currentScreen
            val progress = viewModel.progress
            val currentScreen = viewModel.currentIndex.value + 1
            val totalScreens = viewModel.totalScreens
            val isFirstScreen = viewModel.currentIndex.value == 0

            if (content != null) {
                PracticeContent(
                    content = content,
                    progress = progress,
                    currentScreen = currentScreen,
                    totalScreens = totalScreens,
                    isFirstScreen = isFirstScreen,
                    lessonId = lessonId,
                    onNavigateBack = {
                        if (isFirstScreen) navController.popBackStack(Screen.Home.route, false)
                        else viewModel.previousScreen()
                    },
                    onCancel = { navController.popBackStack(Screen.Home.route, false) },
                    onContinue = {
                        viewModel.nextScreen {
                            navController.navigate(Screen.PracticeCompletion(lessonId).route) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                    }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        // Practice completion screen
        composable(Screen.PracticeCompletion.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val nextLessonId = getNextLessonId(lessonId)
            PracticeCompletionScreen(
                onPopBackStack = { navController.popBackStack() },
                onContinueLesson = { navController.navigate(Screen.LessonLoading(nextLessonId).route) },
                onBackToHome = { navController.popBackStack(Screen.Home.route, inclusive = false) },
                lessonId = lessonId
            )
        }

        // Assessment loading
        composable(Screen.AssessmentLoading.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            AssessmentLoadingScreen(
                lessonId = lessonId,
                onProgress = { navController.navigate(Screen.AssessmentContent(lessonId).route) }
            )
        }

        // Assessment content
        composable(Screen.AssessmentContent.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType }
        )) { backStackEntry ->
            val viewModel: AssessmentViewModel = hiltViewModel()
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val score = viewModel.percentageCorrect.toInt()
            val correct = viewModel.correctAnswers.value
            val total = viewModel.totalScreens
            val time = viewModel.getTimeSpent()
            val navigateToResultsValue = viewModel.navigateToResults.value
            val route = Screen.AssessmentCompletion(lessonId, score, correct, total, time).route

            // Side-effect to trigger navigation once results are ready
            LaunchedEffect(navigateToResultsValue) {
                navigateToResultsValue?.let {
                    navController.navigate(route) {
                        popUpTo(Screen.Assessment.route) { inclusive = true }
                    }
                    viewModel.onResultsNavigated()
                }
            }

            val content = viewModel.currentScreen
            val progress = viewModel.progress
            val currentScreen = viewModel.currentIndex.value + 1
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
                        if (isFirstScreen) navController.popBackStack(Screen.Home.route, false)
                        else viewModel.previousScreen()
                    },
                    onCancel = { navController.popBackStack(Screen.Assessment.route, false) },
                    onContinue = { viewModel.nextScreen() },
                    onResult = { viewModel.updateScore(it) },
                    onStart = { viewModel.startAssessment() },
                    lessonId = lessonId
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        // Final screen: Assessment results + score improvement tracking
        composable(route = Screen.AssessmentCompletion.BASE_ROUTE, arguments = listOf(
            navArgument("lessonId") { type = NavType.StringType },
            navArgument("score") { type = NavType.IntType },
            navArgument("correctAnswers") { type = NavType.IntType },
            navArgument("totalQuestions") { type = NavType.IntType },
            navArgument("timeSpent") { type = NavType.StringType }
        )) { backStackEntry ->
            val lessonId = backStackEntry.arguments!!.getString("lessonId")!!
            val score = backStackEntry.arguments!!.getInt("score")
            val correctAnswers = backStackEntry.arguments!!.getInt("correctAnswers")
            val totalQuestions = backStackEntry.arguments!!.getInt("totalQuestions")
            val timeSpent = backStackEntry.arguments!!.getString("timeSpent")!!

            val completionViewModel: AssessmentCompletionViewModel = hiltViewModel()
            val scoreImprovement by completionViewModel.scoreImprovement.collectAsState()
            //val previousScore by completionViewModel.previousScore.collectAsState()
            val submissionStatus by completionViewModel.submissionStatus.collectAsState()

            AssessmentCompletionScreen(
                onRetakeAssessment = {
                    navController.navigate(Screen.AssessmentLoading(lessonId).route)
                },
                onBackToHome = {
                    navController.navigate(Screen.Assessment.route)
                },
                percentage = score.toFloat(),
                scoreImprovement = scoreImprovement,
                correctAnswers = "$correctAnswers/$totalQuestions",
                timeSpent = timeSpent,
                submissionStatus = submissionStatus,
                onSubmitResults = { completionViewModel.submitResults() },
            )
        }
    }
}

