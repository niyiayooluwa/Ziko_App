package com.ziko.presentation.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.ziko.R
import com.ziko.presentation.components.ProgressTopAppBar
import com.ziko.presentation.components.SuccessIndicator
import com.ziko.ui.model.PracticeScreenContent
import com.ziko.presentation.components.AudioButtonWithLabel
import com.ziko.presentation.components.Size
import com.ziko.presentation.components.SpeechButton
import com.ziko.util.AudioManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PracticeContent(
    content: PracticeScreenContent,
    progress: Float, // 0f to 1f
    onCancel: () -> Unit,
    onContinue: () -> Unit,
    currentScreen: Int,
    totalScreens: Int,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean,
) {

    val expectedText = content.expectedPhrase
    val speechCondition = remember { mutableStateOf<Boolean?>(null) }
    val spokenText = remember { mutableStateOf("") }
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    var permissionDenied by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(AudioManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(AudioManager)
        }
    }

    // Reset state when expected text changes (e.g., when screen flips)
    LaunchedEffect(expectedText) {
        spokenText.value = ""
        speechCondition.value = null
    }

    DisposableEffect(Unit) {
        onDispose {
            debounceJob?.cancel()
        }
    }

    LaunchedEffect(expectedText) {
        speechCondition.value = null
        spokenText.value = ""
    }

    Scaffold (
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
    ){ paddingValues ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
        ){
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Repeat",
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF080E1E)
            )

            Spacer(modifier = Modifier.height(24.dp))

            AudioButtonWithLabel(
                text = "bit - beat",
                assetPath = "lessons/lesson1/about.mp3",
                size = Size.BIG
            )
            
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (permissionDenied) {
                    Text(
                        text = "Please enable microphone permission in settings to use speech recognition",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                SpeechButton(
                    modifier = Modifier,
                    onSpeechResult = { result ->
                        debounceJob?.cancel()
                        debounceJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(500)
                            val normalizedSpoken = result?.trim()?.lowercase() ?: ""
                            val normalizedExpected = expectedText.trim().lowercase()
                            spokenText.value = result ?: ""
                            speechCondition.value = normalizedSpoken == normalizedExpected
                        }
                    },
                    onPermissionDenied = { permissionDenied = true }
                )

                Spacer(modifier = Modifier.height(11.dp))

                Text(
                    text = "Can't speak now",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF656872)
                )
            }

            SuccessIndicator(
                condition = speechCondition.value == true,
                onClick = {
                    if (speechCondition.value == true) {
                        onContinue()
                    } else {
                        spokenText.value = ""
                        speechCondition.value = null
                    }
                }
            )
        }
    }
}