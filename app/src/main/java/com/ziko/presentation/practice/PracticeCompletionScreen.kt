package com.ziko.presentation.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.R
import com.ziko.core.util.UpdateSystemBarsColors
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Pressed

/**
 * A screen displayed after a practice session is completed.
 *
 * Shows a congratulatory message and two CTAs:
 * - Continue to the next lesson (if not the last lesson)
 * - Return to the home screen
 *
 * @param onPopBackStack Callback to pop the navigation stack (used by the back arrow).
 * @param onContinueLesson Callback to navigate to the next lesson in the sequence.
 * @param onBackToHome Callback to navigate back to the home screen.
 * @param lessonId ID of the current lesson (e.g., "lesson1", "lesson8")
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeCompletionScreen(
    onPopBackStack: () -> Unit,
    onContinueLesson: () -> Unit,
    onBackToHome: () -> Unit,
    lessonId: String
) {
    // Extract numeric part of lessonId (e.g., "lesson3" → "3")
    val numPart = lessonId.takeLastWhile { it.isDigit() }
    val nextLesson = numPart.toInt() + 1

    // Update system UI colors for status/navigation bars
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    Scaffold(
        topBar = {
            // Top bar with back arrow (no title text shown)
            CenterAlignedTopAppBar(
                title = { Text(" ") },
                navigationIcon = {
                    IconButton(onClick = onPopBackStack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF410FA3)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
        ) {
            // --- Centered Congratulatory Message + Illustration ---
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(
                        if (lessonId != "lesson8") R.drawable.congratulations
                        else R.drawable.practice_completed
                    ),
                    contentDescription = "Congratulations",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 30.dp)
                )

                Text(
                    text = "Congratulations!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF080e1e),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (lessonId != "lesson8") {
                        "You are moving on to great things."
                    } else {
                        "You have completed the last\nlesson. Do not stop there. Keep\npracticing."
                    },
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF656872)
                )
            }

            // --- Bottom Button Section ---
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                // Continue to next lesson (if not last lesson)
                if (lessonId != "lesson8") {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0xFF5b7bfe))
                            .clickable { onContinueLesson() }
                    ) {
                        Text(
                            text = "Continue to lesson $nextLesson",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                    }
                }

                // Back to home button with neumorphic design
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFFF0eDff))
                        .neumorphic(
                            neuShape = Pressed.Rounded(radius = 4.dp),
                            lightShadowColor = Color.White,
                            darkShadowColor = Color(0xFFd3d3d3),
                            strokeWidth = 4.dp,
                            elevation = 4.dp
                        )
                        .clickable { onBackToHome() }
                ) {
                    Text(
                        text = "Back to home",
                        color = Color(0xFF5b7bfe),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }
}
