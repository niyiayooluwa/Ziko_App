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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.R
import com.ziko.core.util.UpdateSystemBarsColors
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Pressed

/**
 * Composable screen shown upon completing an assessment.
 *
 * Displays the user's score, accuracy, performance commentary, and a summary of key stats.
 * Automatically attempts to submit results upon load, and provides options to retake the assessment
 * or return to the home screen.
 *
 * @param onRetakeAssessment Callback triggered when the user opts to retake the assessment.
 * @param onBackToHome Callback triggered when the user opts to return to the home screen.
 * @param percentage The user's score percentage (0f–100f).
 * @param scoreImprovement Difference in percentage score compared to previous attempts.
 * @param correctAnswers A string summary of how many answers were correct.
 * @param timeSpent A formatted string of how long the assessment took.
 * @param submissionStatus The current submission state (IDLE, SUBMITTING, SUCCESS, ERROR).
 * @param onSubmitResults Called once when the screen loads if submissionStatus is IDLE.
 */
@Composable
fun AssessmentCompletionScreen(
    onRetakeAssessment: () -> Unit,
    onBackToHome: () -> Unit,
    percentage: Float,
    scoreImprovement: Int,
    correctAnswers: String,
    timeSpent: String,
    submissionStatus: SubmissionStatus,
    onSubmitResults: () -> Unit,
) {
    // Set status bar and navigation bar colors
    UpdateSystemBarsColors(
        topColor = Color.White,
        bottomColor = Color.White
    )

    val accuracyOfAssessment = percentage.toInt()

    // Determine visual feedback colors based on score
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

    // Auto-submit results on screen load
    LaunchedEffect(Unit) {
        if (submissionStatus == SubmissionStatus.IDLE) {
            onSubmitResults()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Illustration at the top
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

            // Circular progress showing percentage score
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(204.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                val animatedProgress = remember { Animatable(0f) }

                // Animate the progress bar to the actual percentage
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

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

            // Performance commentary
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = getPerformanceMessage(accuracyOfAssessment),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You've completed the assessment.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(Modifier.height(24.dp))

            // Stats and Action buttons
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

                // Retake and Back buttons
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Retake Assessment Button
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

                    // Back to Home Button
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

/**
 * Returns a performance message string based on accuracy.
 *
 * @param accuracy The percentage accuracy of the user's assessment.
 */
private fun getPerformanceMessage(accuracy: Int): String {
    return when {
        accuracy >= 90 -> "Outstanding!"
        accuracy >= 80 -> "Excellent Work!"
        accuracy >= 70 -> "Great Job!"
        accuracy >= 60 -> "Good Effort!"
        accuracy >= 50 -> "Keep Practicing!"
        else -> "Don't Give Up!"
    }
}

/**
 * Returns a properly formatted improvement percentage string.
 *
 * @param improvement The score difference since the previous attempt.
 * @return A formatted string like "+12%", "-5%", or "No change".
 */
private fun formatImprovementStat(improvement: Int): String {
    return when {
        improvement > 0 -> "+$improvement%"
        improvement < 0 -> "$improvement%"
        else -> "No change"
    }
}

/**
 * A row widget used to display a statistic in key-value format.
 *
 * @param description The name of the stat (e.g. "Correct Answers").
 * @param value The corresponding value of the stat.
 */
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
                Color(0xFF5BA890) // Positive
            } else if (description == "Score Improvement" && value.startsWith("-")) {
                Color(0xFFD6185D) // Negative
            } else {
                Color.Black
            }
        )
    }
}
