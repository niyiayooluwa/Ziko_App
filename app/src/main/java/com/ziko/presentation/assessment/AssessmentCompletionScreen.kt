package com.ziko.presentation.assessment

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.R
import com.ziko.util.UpdateSystemBarsColors
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Pressed

@Composable
fun AssessmentCompletionScreen(
    onRetakeAssessment: () -> Unit,
    onBackToHome: () -> Unit,
    percentage: Float,
    scoreImprovement: Int,
    correctAnswers: String,
    timeSpent: String,
    previousScore: Int?,
    submissionStatus: SubmissionStatus,
    onSubmitResults: () -> Unit,
    onRetrySubmission: () -> Unit
) {
    UpdateSystemBarsColors(
        topColor = Color.White,
        bottomColor = Color.White
    )

    val accuracyOfAssessment = percentage.toInt()
    val colorPrimary = when (accuracyOfAssessment) {
        in 70..100 -> Color(0xFF5BA890)
        in 50..69 -> Color(0xFFF76400)
        else -> Color(0xFFD6185D)
    }
    val colorSecondary = when (accuracyOfAssessment) {
        in 0..49 -> Color(0xFFf7d1df)
        in 50..69 -> Color(0xFFf7d1df)
        else -> Color(0xFFDeeee9)
    }

    // Auto-submit results when screen loads
    LaunchedEffect(Unit) {
        if (submissionStatus == SubmissionStatus.IDLE) {
            onSubmitResults()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.assessment_complete),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(248.dp)
                .align(Alignment.TopCenter)
                .offset(x = (-8).dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(204.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                val animatedProgress = remember { Animatable(0f) }
                LaunchedEffect(accuracyOfAssessment) {
                    val targetValue = (accuracyOfAssessment / 100f).coerceIn(0f, 1f)
                    animatedProgress.animateTo(
                        targetValue = targetValue,
                        animationSpec = tween(durationMillis = 1000)
                    )
                }

                CircularProgressIndicator(
                    progress = { animatedProgress.value },
                    modifier = Modifier.fillMaxSize(),
                    color = colorPrimary,
                    strokeWidth = 23.dp,
                    trackColor = colorSecondary,
                    strokeCap = StrokeCap.Round,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$accuracyOfAssessment%",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimary
                    )
                    Text(
                        text = "Accuracy",
                        fontSize = 16.sp,
                        color = colorPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Commentary with submission status
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = getPerformanceMessage(accuracyOfAssessment, scoreImprovement),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                /*// Show score improvement if available
                if (previousScore != null && scoreImprovement != 0) {
                    Text(
                        text = getImprovementMessage(scoreImprovement),
                        fontSize = 14.sp,
                        color = if (scoreImprovement > 0) Color(0xFF5BA890) else Color(0xFFF76400),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }*/

                Text(
                    text = "You've completed the assessment.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                /*// Submission status indicator
                SubmissionStatusIndicator(
                    status = submissionStatus,
                    onRetry = onRetrySubmission
                )*/
            }

            Spacer(Modifier.height(24.dp))

            // Stats and Buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Stats
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF6EB), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Let's see how you did and where you can improve:",
                        fontSize = 13.sp,
                        color = Color(0xFF656872),
                        fontWeight = FontWeight.Normal
                    )

                    StatRow("Score Improvement", formatImprovementStat(scoreImprovement))
                    StatRow("Correct Answers", correctAnswers)
                    StatRow("Time spent", timeSpent)
                }

                Spacer(Modifier.height(38.dp))

                // Buttons
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Retake Assessment button
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0xFF5b7bfe))
                            .clickable { onRetakeAssessment() }
                    ) {
                        Text(
                            text = "Retake Assessment",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                    }

                    // Back to Home button
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
}

@Composable
private fun SubmissionStatusIndicator(
    status: SubmissionStatus,
    onRetry: () -> Unit
) {
    when (status) {
        SubmissionStatus.SUBMITTING -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = Color(0xFF5b7bfe)
                )
                Text(
                    text = "Saving your score...",
                    fontSize = 12.sp,
                    color = Color(0xFF5b7bfe)
                )
            }
        }
        SubmissionStatus.SUCCESS -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF5BA890)
                )
                Text(
                    text = "Score saved successfully!",
                    fontSize = 12.sp,
                    color = Color(0xFF5BA890)
                )
            }
        }
        SubmissionStatus.ERROR -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFF76400)
                    )
                    Text(
                        text = "Failed to save score",
                        fontSize = 12.sp,
                        color = Color(0xFFF76400)
                    )
                }

                Text(
                    text = "Tap to retry",
                    fontSize = 10.sp,
                    color = Color(0xFF5b7bfe),
                    modifier = Modifier
                        .clickable { onRetry() }
                        .padding(4.dp),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        SubmissionStatus.IDLE -> {
            // Show nothing when idle
        }
    }
}

private fun getPerformanceMessage(accuracy: Int, improvement: Int): String {
    return when {
        accuracy >= 90 -> "Outstanding!"
        accuracy >= 80 -> "Excellent Work!"
        accuracy >= 70 -> "Great Job!"
        accuracy >= 60 -> "Good Effort!"
        accuracy >= 50 -> "Keep Practicing!"
        else -> "Don't Give Up!"
    }
}

private fun formatImprovementStat(improvement: Int): String {
    return when {
        improvement > 0 -> "+$improvement%"
        improvement < 0 -> "$improvement%"
        else -> "No change"
    }
}

@Composable
fun StatRow(description: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = description,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            color = Color(0xFF363b44)
        )

        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (description == "Score Improvement" && value.startsWith("+")) {
                Color(0xFF5BA890) // Green for positive improvement
            } else if (description == "Score Improvement" && value.startsWith("-")) {
                Color(0xFFD6185D) // Red for negative improvement
            } else {
                Color.Black
            }
        )
    }
}