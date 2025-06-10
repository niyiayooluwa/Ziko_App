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
import com.ziko.ui.model.PracticeScreenContent
import com.ziko.util.AudioManager
import com.ziko.util.normalizeText

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

    // Speech recognition state
    val spokenText = remember { mutableStateOf("") }
    val speechCondition = remember { mutableStateOf<Boolean?>(null) }
    val hasRecordedSpeech = remember { mutableStateOf(false) } // Track if user has recorded
    val attemptCount = remember { mutableIntStateOf(0) } // Track number of attempts
    val maxAttempts = 3 // Maximum attempts before allowing skip
    var permissionDenied by remember { mutableStateOf(false) }

    // AudioManager lifecycle management
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        Log.d("AudioManager", "Observer force-added")
        lifecycleOwner.lifecycle.removeObserver(AudioManager) // Just in case
        lifecycleOwner.lifecycle.addObserver(AudioManager)
    }

    // Reset speech state when expected text changes
    LaunchedEffect(expectedText) {
        Log.d("PracticeContent", "Expected text changed: $expectedText")
        spokenText.value = ""
        speechCondition.value = null
        hasRecordedSpeech.value = false
        attemptCount.intValue = 0
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
            // Main content area
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                // Instructions and audio section
                Column (
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Instruction text
                    Text(
                        text = content.instructions,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF080E1E)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Audio playback button if sound is available
                    if (content.sound != null) {
                        if (lessonId != "lesson8") {
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

                // Speech input section
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Permission denied message
                    if (permissionDenied) {
                        Text(
                            text = "Please enable microphone permission in settings",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Show attempt counter
                    if (attemptCount.intValue > 0) {
                        Text(
                            text = "Attempt ${attemptCount.intValue} of $maxAttempts",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF656872)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Speech input button
                    SpeechButton(
                        modifier = Modifier,
                        onSpeechResult = { result ->
                            Log.d("PracticeContent", "Speech result received: $result")
                            spokenText.value = result ?: ""
                            hasRecordedSpeech.value = !result.isNullOrBlank() // Mark as recorded if we got text
                            speechCondition.value = null // Reset evaluation state
                        },
                        onPermissionDenied = {
                            Log.d("PracticeContent", "Permission denied")
                            permissionDenied = true
                        }
                    )

                    Spacer(modifier = Modifier.height(11.dp))

                    // Helper text
                    Text(
                        text = "Can't speak now",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF656872)
                    )
                }

                // Bottom spacing to avoid UI overlap
                Spacer(modifier = Modifier.height(80.dp))
            }

            // Bottom evaluation bar
            SuccessIndicator(
                modifier = Modifier
                    //.align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                condition = speechCondition.value,
                hasRecorded = hasRecordedSpeech.value,
                attemptCount = attemptCount.intValue,
                maxAttempts = maxAttempts,
                onClick = {
                    Log.d("PracticeContent", "SuccessIndicator clicked")

                    when (speechCondition.value) {
                        null -> {
                            // Only evaluate if user has recorded speech
                            if (hasRecordedSpeech.value) {
                                Log.d("PracticeContent", "Evaluating speech: '${spokenText.value}' vs '$expectedText'")

                                val normalizedSpoken = normalizeText(spokenText.value)
                                val normalizedExpected = normalizeText(expectedText)
                                val isCorrect = normalizedSpoken == normalizedExpected

                                attemptCount.intValue += 1
                                Log.d("PracticeContent", "Speech evaluation result: $isCorrect, Attempt: ${attemptCount.intValue}")
                                speechCondition.value = isCorrect
                            }
                        }
                        true -> {
                            // Correct answer - continue to next screen
                            Log.d("PracticeContent", "Continuing to next screen")
                            onContinue()
                        }
                        false -> {
                            if (attemptCount.intValue >= maxAttempts) {
                                // Max attempts reached - allow skip to next screen
                                Log.d("PracticeContent", "Max attempts reached, skipping to next screen")
                                onContinue()
                            } else {
                                // Incorrect - reset for retry
                                Log.d("PracticeContent", "Resetting for retry")
                                spokenText.value = ""
                                speechCondition.value = null
                                hasRecordedSpeech.value = false
                            }
                        }
                    }
                }
            )
        }
    }
}
