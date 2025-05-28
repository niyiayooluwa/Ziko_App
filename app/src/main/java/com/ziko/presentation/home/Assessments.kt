package com.ziko.presentation.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ziko.data.model.AssessmentCard
import com.ziko.navigation.Screen
import com.ziko.presentation.components.FloatingNavBar

@Composable
fun AssessmentScreen(
    navController: NavController,
    userName: String = "Sam" // Default or from ViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scrollState = rememberScrollState()
    val purpleColor = Color(0xFF410FA3) // Purple color for top app bar

    // Root Box that contains everything
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Purple Top App Bar that doesn't scroll (matching the LessonScreen)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(purpleColor),
                color = purpleColor
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 48.dp,
                            bottom = 16.dp
                        ),
                ) {
                    // Profile picture placeholder
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName.first().toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "Assessment",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.White
                    )

                    Text(
                        text = "Test your knowledge and sharpen your\npronunciation skills",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }
            }

            // Scrollable content
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(
                        top = 24.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 70.dp
                    )
            ) {
                // Assessments Section
                Text(
                    text = "Drill by Topic",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    assessments.forEach { assessment ->
                        AssessmentCard(
                            title = assessment.title,
                            highestScore = assessment.highestScore,
                            accuracy = assessment.accuracy,
                            onClick = {
                                val lessonId = assessment.id
                                navController.navigate(Screen.AssessmentLoading(lessonId).route)
                            },
                        )
                    }
                }
            }
        }

        // Floating Navigation Bar positioned at the bottom center
        if (currentRoute in listOf(Screen.Home.route, Screen.Assessment.route)) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                FloatingNavBar(
                    navController = navController,
                    currentRoute = currentRoute ?: Screen.Assessment.route
                )
            }
        }
    }
}


@Composable
fun AssessmentCard(
    title: String,
    highestScore: Int?,
    accuracy: Float?,
    onClick: () -> Unit
) {
    val accuracyVar = accuracy?.times(100)?.toInt()

    val colorPrimary = when (accuracyVar) {
        null -> Color(0xFFE5E5E5)
        in 70..100 -> Color(0xFF5BA890)
        else -> Color(0xFFD6185D)
    }
    val colorSecondary = when (accuracyVar) {
        null -> Color(0xFFf3f3f4)
        in 0..69 -> Color(0xFFf7d1df)
        else -> Color(0xFFDeeee9)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Add padding here
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(end = 16.dp) // Add spacing between text and progress
            ) {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (highestScore != null) {
                    Text(
                        text = "Highest Score: ${highestScore}%",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF656872)
                    )
                } else {
                    Text(
                        text = "Highest Score: Not attempted",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF656872)
                    )
                }
            }

            Box(
                modifier = Modifier.size(72.dp)
            ) {
                if (highestScore != null) {
                    if (accuracy != null) {
                        CircularProgressIndicator(
                            progress = { accuracy },
                            modifier = Modifier.fillMaxSize().align(Alignment.Center),
                            color = colorPrimary,
                            strokeWidth = 8.43.dp,
                            trackColor = colorSecondary,
                            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = "$accuracyVar%",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorPrimary
                        )
                        Text(
                            text = "Accuracy",
                            fontSize = 8.64.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF656872),
                            modifier = Modifier.offset(y = (-8).dp)
                        )
                    }
                } else {
                    CircularProgressIndicator(
                        progress = { 0.5f },
                        modifier = Modifier.fillMaxSize().align(Alignment.Center),
                        color = Color(0xFFe5e5e5),
                        strokeWidth = 8.43.dp,
                        trackColor = Color(0xFFececec),
                        strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = "N/A",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFe5e5e5)
                        )

                        Text(
                            text = "Accuracy",
                            fontSize = 8.64.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFe5e5e5)
                        )
                    }
                }
            }
        }
    }
}

val assessments = listOf(
    AssessmentCard(
        "Monophthongs",
        highestScore = 62,
        accuracy = 0.72f,
        id = "lesson1"
    ),
    AssessmentCard(
        "Diphthongs",
        highestScore = 58,
        accuracy = 0.65f,
        id = "lesson2"
    ),
    AssessmentCard(
        "Triphthongs",
        highestScore = null,  // No score
        accuracy = null,     // No accuracy
        id = "lesson3"
    ),
    AssessmentCard(
        "Voiced Consonants",
        highestScore = 74,
        accuracy = 0.81f,
        id = "lesson4"
    ),
    AssessmentCard(
        "Voiceless Consonants",
        highestScore = 89,
        accuracy = 0.92f,
        id = "lesson5"
    ),
    AssessmentCard(
        "Intonation",
        highestScore = null,  // No score
        accuracy = null,       // No accuracy
        id = "lesson6"
    ),
    AssessmentCard(
        "Stress",
        highestScore = 67,
        accuracy = 0.71f,
        id = "lesson7"
    ),
    AssessmentCard(
        "Rhythm",
        highestScore = null,  // No score
        accuracy = null,      // No accuracy
        id = "lesson8"
    )
)