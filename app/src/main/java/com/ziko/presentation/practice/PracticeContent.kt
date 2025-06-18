package com.ziko.presentation.practice

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ziko.presentation.components.AudioButtonWithLabel
import com.ziko.presentation.components.AudioButtonWithLabelForLesson8
import com.ziko.presentation.components.ProgressTopAppBar
import com.ziko.presentation.components.Size
import com.ziko.presentation.components.SpeechButton
import com.ziko.presentation.components.SuccessIndicator
import com.ziko.presentation.components.rememberSpeechButtonController
import com.ziko.ui.model.PracticeScreenContent
import com.ziko.core.util.AudioManager
import com.ziko.core.util.UpdateSystemBarsColors
import com.ziko.core.util.normalizeText

/**
 * Composable function that displays the main UI for practicing a spoken phrase.
 *
 * This screen includes instructions, audio playback (if applicable), speech input via microphone,
 * attempt-based evaluation, dynamic progress tracking, and branching UI based on lesson alignment.
 *
 * @param content The `PracticeScreenContent` containing the instructions, expected phrase, audio, and alignment.
 * @param progress A float value representing the current progress of the practice flow (0f..1f).
 * @param onCancel Callback invoked when the user cancels the practice session.
 * @param onContinue Callback invoked when the user successfully completes the current screen or skips after max attempts.
 * @param currentScreen The current index of the practice screen.
 * @param totalScreens The total number of screens in the practice flow.
 * @param onNavigateBack Callback invoked when the user taps the back navigation icon.
 * @param isFirstScreen Boolean indicating if the current screen is the first in the flow.
 * @param lessonId The ID of the lesson (used to modify behavior or visuals based on specific lessons).
 */
@Composable
fun PracticeContent(
    content: PracticeScreenContent,
    progress: Float,
    onCancel: () -> Unit,
    onContinue: () -> Unit,
    currentScreen: Int,
    totalScreens: Int,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean,
    lessonId: String
) {
    Log.d("PracticeContent", "Composing PracticeContent")

    val expectedText = content.expectedPhrase

    // ---- State Management ----

    val spokenText = remember { mutableStateOf("") } // Stores user’s spoken input
    val speechCondition = remember { mutableStateOf<Boolean?>(null) } // null = untested, true/false = evaluated
    val hasRecordedSpeech = remember { mutableStateOf(false) } // Tracks if user has spoken
    val attemptCount = remember { mutableIntStateOf(0) } // Tracks how many attempts user has made
    val maxAttempts = 3 // Maximum attempts allowed before skipping
    var permissionDenied by remember { mutableStateOf(false) } // Tracks if mic permission was denied
    val speechButtonController = rememberSpeechButtonController() // Manages state of the speech button (active, completed, etc.)

    val lifecycleOwner = LocalLifecycleOwner.current

    // Determines bottom system nav bar color based on current speech evaluation state
    val navBarColor = when (speechCondition.value) {
        true -> Color(0xFF12D18E)  // Green for correct
        false -> Color(0xFFf75555) // Red for wrong
        null -> Color.White        // Default white
    }

    // Update system UI bars for immersive design
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = navBarColor
    )

    // Attach AudioManager to lifecycle
    LaunchedEffect(Unit) {
        Log.d("AudioManager", "Observer force-added")
        lifecycleOwner.lifecycle.removeObserver(AudioManager)
        lifecycleOwner.lifecycle.addObserver(AudioManager)
    }

    // Reset state on text change (i.e., new screen/phrase)
    LaunchedEffect(expectedText) {
        spokenText.value = ""
        speechCondition.value = null
        hasRecordedSpeech.value = false
        attemptCount.intValue = 0
        speechButtonController.enable()
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // ---- Main Content Area ----
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                // ---- Instructional Section ----
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = content.instructions,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF080E1E)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Conditional audio playback section
                    if (content.sound != null) {
                        if (content.alignment != true) {
                            AudioButtonWithLabel(
                                text = content.sound.first,
                                assetPath = content.sound.second,
                                size = Size.BIG
                            )
                        } else {
                            AudioButtonWithLabelForLesson8(
                                text = content.sound.first,
                                assetPath = content.sound.second,
                                size = Size.BIG
                            )
                        }
                    }
                }

                // ---- Speech Input Area ----
                if (!content.alignment) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (permissionDenied) {
                            Text(
                                text = "Please enable microphone permission in settings",
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        if (attemptCount.intValue > 0) {
                            Text(
                                text = "Attempt ${attemptCount.intValue} of $maxAttempts",
                                fontSize = 14.sp,
                                color = Color(0xFF656872)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        SpeechButton(
                            modifier = Modifier,
                            onSpeechResult = { result ->
                                Log.d("PracticeContent", "Speech result received: $result")
                                spokenText.value = result ?: ""
                                hasRecordedSpeech.value = !result.isNullOrBlank()
                                speechCondition.value = null
                                if (!result.isNullOrBlank()) speechButtonController.setCompleted()
                            },
                            onPermissionDenied = {
                                Log.d("PracticeContent", "Permission denied")
                                permissionDenied = true
                            },
                            controller = speechButtonController
                        )

                        Spacer(modifier = Modifier.height(11.dp))

                        Text(
                            text = "Can't speak now",
                            fontSize = 15.sp,
                            color = Color(0xFF656872)
                        )
                    }
                }
            }

            // ---- Bottom Bar Section ----
            if (!content.alignment) {
                // Regular layout (not aligned)
                SuccessIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    condition = speechCondition.value,
                    hasRecorded = hasRecordedSpeech.value,
                    attemptCount = attemptCount.intValue,
                    maxAttempts = maxAttempts,
                    onClick = {
                        when (speechCondition.value) {
                            null -> {
                                if (hasRecordedSpeech.value) {
                                    val normalizedSpoken = normalizeText(spokenText.value)
                                    val normalizedExpected = normalizeText(expectedText)
                                    val isCorrect = normalizedSpoken == normalizedExpected
                                    attemptCount.intValue += 1
                                    speechCondition.value = isCorrect
                                }
                            }
                            true -> onContinue()
                            false -> {
                                if (attemptCount.intValue >= maxAttempts) {
                                    onContinue()
                                } else {
                                    spokenText.value = ""
                                    speechCondition.value = null
                                    hasRecordedSpeech.value = false
                                    speechButtonController.enable()
                                }
                            }
                        }
                    }
                )
            } else {
                // Special layout for aligned (lesson 8, etc.)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        if (permissionDenied) {
                            Text(
                                text = "Please enable microphone permission in settings",
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        if (attemptCount.intValue > 0) {
                            Text(
                                text = "Attempt ${attemptCount.intValue} of $maxAttempts",
                                fontSize = 14.sp,
                                color = Color(0xFF656872)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Can't speak now",
                            fontSize = 15.sp,
                            color = Color(0xFF656872)
                        )

                        Spacer(modifier = Modifier.height(11.dp))

                        SpeechButton(
                            modifier = Modifier,
                            onSpeechResult = { result ->
                                spokenText.value = result ?: ""
                                hasRecordedSpeech.value = !result.isNullOrBlank()
                                speechCondition.value = null
                                if (!result.isNullOrBlank()) speechButtonController.setCompleted()
                            },
                            onPermissionDenied = {
                                permissionDenied = true
                            },
                            controller = speechButtonController
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    SuccessIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        condition = speechCondition.value,
                        hasRecorded = hasRecordedSpeech.value,
                        attemptCount = attemptCount.intValue,
                        maxAttempts = maxAttempts,
                        onClick = {
                            when (speechCondition.value) {
                                null -> {
                                    if (hasRecordedSpeech.value) {
                                        val normalizedSpoken = normalizeText(spokenText.value)
                                        val normalizedExpected = normalizeText(expectedText)
                                        val isCorrect = normalizedSpoken == normalizedExpected
                                        attemptCount.intValue += 1
                                        speechCondition.value = isCorrect
                                    }
                                }
                                true -> onContinue()
                                false -> {
                                    if (attemptCount.intValue >= maxAttempts) {
                                        onContinue()
                                    } else {
                                        spokenText.value = ""
                                        speechCondition.value = null
                                        hasRecordedSpeech.value = false
                                        speechButtonController.enable()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

