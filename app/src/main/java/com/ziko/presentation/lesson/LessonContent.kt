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
import com.ziko.presentation.components.ProgressTopAppBar
import com.ziko.ui.model.LessonScreenContent
import com.ziko.presentation.components.AudioButtonWithLabel
import com.ziko.presentation.components.Size
import com.ziko.core.util.AudioManager
import com.ziko.core.util.UpdateSystemBarsColors

/**
 * Composable that renders a single screen of lesson content in a step-by-step guided flow.
 *
 * This screen is used during a lesson walkthrough, supporting both text and audio-based content.
 * It is meant to be reused across all lesson steps, with content dynamically injected via the
 * [LessonScreenContent] data class.
 *
 * Core features:
 * - Lifecycle-aware registration of the [AudioManager] (auto stops on screen change).
 * - Dynamic rendering of:
 *   - Main text content.
 *   - Optional audio + text buttons.
 *   - Optional bold title section.
 *   - Optional subtext.
 *   - List of audio-enhanced option buttons.
 * - A continue button that the user can tap to move to the next lesson screen.
 *
 * @param content The dynamic lesson content displayed on this screen, including audio and text data.
 * @param progress Progress value between 0f and 1f indicating the user's lesson progress.
 * @param onCancel Callback triggered when the cancel button is pressed in the app bar.
 * @param onContinue Callback triggered when the continue button is pressed.
 * @param currentScreen The current step index (1-based).
 * @param totalScreens Total number of steps in the lesson (for progress bar).
 * @param onNavigateBack Callback to handle backward navigation.
 * @param isFirstScreen True if this screen is the first in the lesson flow (disables back icon).
 */
@Composable
fun LessonContent(
    content: LessonScreenContent,
    progress: Float, // Range: 0f to 1f
    onCancel: () -> Unit,
    onContinue: () -> Unit,
    currentScreen: Int,
    totalScreens: Int,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean,
) {
    // Set system bar colors specific to lesson context
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    // Attach AudioManager to lifecycle to handle play/pause/resume as needed
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(AudioManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(AudioManager)
        }
    }

    Scaffold(
        topBar = {
            // Custom top app bar showing progress and cancel/back controls
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Main text block: usually the instruction or context
            Text(
                text = content.mainText,
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Either render an audio button OR bold highlight text
            if (content.sound != null) {
                // Audio button with large size if available
                AudioButtonWithLabel(
                    text = content.sound.first,
                    assetPath = content.sound.second,
                    size = Size.BIG
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                // Optional bold text (e.g., a word or sentence being emphasized)
                content.boldText?.let {
                    Text(
                        text = it,
                        color = Color(0xFF080E1E),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Optional subtext section (e.g., definitions or explanations)
            content.subText?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF080E1E)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // List of audio-enhanced options (e.g., pronunciation of similar words)
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                content.options.forEach { option ->
                    AudioButtonWithLabel(
                        text = option.first,
                        assetPath = option.second,
                        size = Size.SMALL
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Spacer to push continue button to bottom
            Spacer(modifier = Modifier.weight(1f))

            // Continue button: advances to the next screen in lesson flow
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF5B7BFE))
                    .clickable { onContinue() }
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
