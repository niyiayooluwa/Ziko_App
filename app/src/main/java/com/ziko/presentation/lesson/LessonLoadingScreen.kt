package com.ziko.presentation.lesson

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ziko.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun LessonLoadingScreen(
    navController: NavController,
    lessonId: String
) {
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate(Screen.LessonIntro(lessonId).route)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
