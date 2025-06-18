package com.ziko.presentation.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.ziko.data.model.LessonDataProvider
import com.ziko.data.model.LessonIntroContentProvider
import com.ziko.navigation.Screen
import com.ziko.presentation.components.ProgressTopAppBar
import com.ziko.ui.model.LessonIntroContent
import com.ziko.presentation.components.AudioButtonWithLabelForIntro
import com.ziko.core.util.AudioManager
import com.ziko.core.util.UpdateSystemBarsColors

/**
 * Composable screen that represents the introductory page for a lesson.
 *
 * This screen is the first step in the lesson flow and typically includes:
 * - A pronunciation/audio definition segment.
 * - One or more key takeaway points (if available).
 * - A "Continue" button to begin the actual lesson content.
 *
 * It also sets up system UI theming, lifecycle binding for audio playback, and a progress app bar.
 *
 * @param navController Used to navigate to the first actual content screen of the lesson.
 * @param lessonId The ID of the lesson being started. Used to fetch relevant intro content.
 * @param onCancel Callback when the user cancels the lesson flow entirely.
 * @param onNavigateBack Callback when the user navigates back using the back arrow.
 * @param isFirstScreen Whether this is the first screen in the lesson flow (used to toggle back icon visibility).
 */
@Composable
fun LessonIntroScreen(
    navController: NavController,
    lessonId: String,
    onCancel: () -> Unit,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean
) {
    // Apply consistent system bar colors for lesson screens
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    // --- Fetch content & progress tracking ---
    // Get lesson intro content (definition audio and texts)
    val introContent: LessonIntroContent =
        LessonIntroContentProvider.getIntroContent(lessonId)

    // Total lesson screens = 1 intro screen + content screens
    val totalScreens = 1 + LessonDataProvider.getLessonContent(lessonId).size

    // Progress for intro screen is always 1/totalScreens
    val progress = 1f / totalScreens
    val currentScreen = 1

    // Bind AudioManager to lifecycle (auto stop/resume playback)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(AudioManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(AudioManager)
        }
    }

    // Scaffold with a custom top app bar showing progress and cancel/back actions
    Scaffold(
        topBar = {
            ProgressTopAppBar(
                progress = progress,
                currentScreen = currentScreen,
                totalScreens = totalScreens,
                onCancel = onCancel,
                onNavigateBack = onNavigateBack,
                isFirstScreen = isFirstScreen
            )
        }
    ) { paddingValues ->

        // Main content column
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // --- Audio definition section ---
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.Start
            ) {
                AudioButtonWithLabelForIntro(
                    textOne = introContent.definitionTextOne,
                    textTwo = introContent.definitionTextTwo,
                    assetPath = introContent.definitionAudio
                )
            }

            // --- Optional bullet points or learning objectives ---
            introContent.points?.forEach { point ->
                if (point != null) {
                    Text(
                        text = point,
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp,
                        color = Color(0xFF080e1e)
                    )
                }
            }

            // Push continue button to bottom
            Spacer(Modifier.weight(1f))

            // --- Continue to Lesson Content Button ---
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF5B7BFE))
                    .clickable {
                        // Navigate to the actual lesson content screen
                        navController.navigate(Screen.LessonContent(lessonId).route)
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