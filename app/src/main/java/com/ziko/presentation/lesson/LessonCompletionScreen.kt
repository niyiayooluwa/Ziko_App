package com.ziko.presentation.lesson

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
 * Displays the completion screen shown after a user finishes a lesson.
 *
 * This screen acknowledges the user's progress and offers two choices:
 * - Continue to the practice exercises related to the completed lesson.
 * - Return to the home screen.
 *
 * It also displays a dynamic message based on the lesson ID, such as "You have completed Lesson 2."
 *
 * The layout consists of:
 * - A top app bar with a back button.
 * - A centered congratulatory image and message.
 * - Two action buttons fixed to the bottom of the screen.
 *
 * @param onPopBackStack Callback triggered when the user presses the back button.
 * @param onContinuePractice Callback triggered when the user selects to continue to practice.
 * @param onBackToHome Callback triggered when the user selects to go back to the home screen.
 * @param lessonId A string representing the lesson's identifier (e.g., "lesson2").
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonCompletionScreen(
    onPopBackStack: () -> Unit,
    onContinuePractice: () -> Unit,
    onBackToHome: () -> Unit,
    lessonId: String
) {
    // Set system UI colors for top and bottom system bars
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(" ") }, // Empty title for a minimal header
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
                .padding(24.dp)
        ) {
            // Centered message section
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(R.drawable.congratulations),
                    contentDescription = "Congratulations",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 30.dp)
                )

                Text(
                    text = "Lesson Completed",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF080e1e)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Parse lessonId into readable lesson label
                val textPart = lessonId.takeWhile { it.isLetter() }
                    .replaceFirstChar { it.uppercase() }
                val numPart = lessonId.takeLastWhile { it.isDigit() }

                Text(
                    text = "You have completed $textPart $numPart. You \nare ready for a bigger challenge.",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF656872)
                )
            }

            // Bottom buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                // Primary action: Continue practice
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFF5b7bfe))
                        .clickable { onContinuePractice() }
                ) {
                    Text(
                        text = "Continue to practice exercise",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }

                // Secondary action: Back to home with neumorphic styling
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
