package com.ziko.presentation.practice

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.R
import com.ziko.core.util.UpdateSystemBarsColors
import kotlinx.coroutines.delay


/**
 * Composable function that displays an animated loading screen after a lesson is completed,
 * before transitioning to the next phase (e.g., assessment or summary).
 *
 * This screen features a top app bar, a progress bar that animates over 3 seconds,
 * a congratulatory message, and a lesson-specific illustration.
 *
 * @param lessonId The ID of the completed lesson (e.g., "lesson8" or "L8") used for display.
 * @param onProgress Callback triggered after the progress animation finishes (approx. 3s + 40ms).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeLoadingScreen(
    lessonId: String,
    onProgress: () -> Unit
) {
    // Set system status and navigation bar colors
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    // Extract lesson display format: "Lesson 8", "L 8", etc.
    val textPart = lessonId.takeWhile { it.isLetter() }.replaceFirstChar { it.uppercase() }
    val numPart = lessonId.takeLastWhile { it.isDigit() }
    val lessonIdentifier = "$textPart $numPart"

    // Progress bar animation controller
    val progress = remember { Animatable(0.02f) }

    // Launch once on entering screen: animate progress and trigger next phase
    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000) // Smooth progress for 3s
        )
        delay(40) // Give small buffer after animation
        onProgress()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF410fa3),
                ),
                title = { Text(text = "") } // No title text, purely visual
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 50.dp)
            ) {
                // ---- Illustration ----
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.practice_loading_illustration),
                        contentDescription = "Lesson Illustration",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(278.dp)
                            .width(240.dp)
                    )
                }

                // ---- Progress Indicator + Message ----
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Loading...",
                        fontSize = 28.sp,
                        color = Color(0xFF080E1E),
                        fontWeight = FontWeight.ExtraBold
                    )

                    LinearProgressIndicator(
                        progress = { progress.value }, // Animated progress state
                        modifier = Modifier
                            .height(11.dp)
                            .fillMaxWidth(),
                        color = Color(0xFF5B7BFE),     // Blue fill
                        trackColor = Color(0xFFe5e5e5), // Gray background
                        strokeCap = StrokeCap.Round
                    )

                    Text(
                        text = "You have completed $lessonIdentifier. You are ready\nfor a bigger challenge.",
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF656872)
                    )
                }
            }
        }
    }
}
