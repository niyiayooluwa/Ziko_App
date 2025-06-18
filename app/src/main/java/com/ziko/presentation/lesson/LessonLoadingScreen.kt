package com.ziko.presentation.lesson

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.R
import com.ziko.core.util.UpdateSystemBarsColors
import com.ziko.data.model.LessonDataProvider
import com.ziko.navigation.Screen

/**
 * Composable that displays a themed loading screen for a lesson.
 *
 * This screen acts as a visual bridge between lesson selection and the actual lesson content.
 * It shows:
 * - A header with the lesson title.
 * - A relevant illustration based on the lesson ID.
 * - A "Continue" button to proceed to the Lesson Intro screen.
 *
 * System bar colors are themed to match the lesson environment.
 *
 * @param navController NavController used for navigating to the LessonIntro screen.
 * @param lessonId Unique identifier for the selected lesson. Drives dynamic content selection.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonLoadingScreen(
    navController: NavController,
    lessonId: String,
) {
    // Set consistent lesson theme system bar colors
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    // Retrieve lesson metadata (e.g. title) once based on lessonId
    val lesson = remember(lessonId) {
        LessonDataProvider.getLessonInfo(lessonId)
    }

    // Main scaffold with a top app bar and content area
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF410fa3),
                ),
                title = {
                    lesson?.let {
                        Text(
                            text = it.title,
                            fontSize = 22.sp,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Home.route)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        // Root layout with illustration and continue button
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        ) {

            // --- Illustration Logic ---
            Image(
                painter = painterResource(
                    id = when (lessonId) {
                        "lesson1" -> R.drawable.monopthong_illustration
                        "lesson2" -> R.drawable.diph
                        "lesson3" -> R.drawable.tri
                        "lesson4" -> R.drawable.voiced
                        "lesson5" -> R.drawable.voiceless
                        "lesson6" -> R.drawable.intonation
                        "lesson7" -> R.drawable.stress
                        "lesson8" -> R.drawable.rhythm
                        else -> R.drawable.monopthong_illustration
                    }
                ),
                contentDescription = "Lesson Illustration",
                modifier = Modifier
                    .size(420.dp)
                    .align(Alignment.Center)
            )

            // --- Continue Button ---
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(56.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF5B7BFE))
                    .clickable {
                        navController.navigate(Screen.LessonIntro(lessonId).route)
                    }
            ) {
                Text(
                    text = "Continue",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}

