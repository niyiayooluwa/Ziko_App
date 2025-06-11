package com.ziko.presentation.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.ziko.data.model.LessonCard
import com.ziko.navigation.Screen
import com.ziko.presentation.components.FloatingNavBar
import com.ziko.presentation.profile.UserViewModel

@Composable
fun LessonScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val user by userViewModel.user.collectAsState()
    val profilePicUri by userViewModel.profilePicUri.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scrollState = rememberScrollState()
    val purpleColor = Color(0xFF410FA3)

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
                            .clickable{navController.navigate(Screen.Profile.route)}
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (profilePicUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = profilePicUri),
                                contentDescription = null,
                                contentScale = (ContentScale.Crop),
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        }
                        else {
                            Text(
                                text = user?.first_name?.first().toString(),
                                fontSize = 13.sp,
                                color = Color(0xFF5b7bfe),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Text(
                        text = "Hello, ${user?.first_name}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.White
                    )

                    Text(
                        text = "What would you like to learn today?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }
            }

            // Scrollable content
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(
                        top = 24.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 70.dp
                    )
            ) {
                Text(
                    text = "All Lessons",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black,
                )

                // Lessons List
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    lessons.forEach { lesson ->
                        LessonCard(
                            title = lesson.title,
                            description = lesson.description,
                            color = lesson.color,
                            onClick = {
                                val lessonId = lesson.id
                                if (lessonId != "lesson1") {
                                    navController.navigate(Screen.LessonLoading(lessonId).route)
                                } else {
                                    navController.navigate(Screen.PreLessonLoading(lessonId).route)
                                }
                            }
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
                    currentRoute = currentRoute ?: Screen.Home.route
                )
            }
        }
    }
}

@Composable
fun LessonCard(
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        //elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                color = Color(0xFF656872)
            )
        }
    }
}

val lessons = listOf(
    LessonCard(
        "Monophthongs",
        "Learn the 7 short & 5 long vowel sounds in English pronunciation",
        "lesson1",
        Color(0xFFf2f3f3)
    ),
    LessonCard(
        "Diphthongs",
        "Master gliding vowel sounds like /al/, /el/, and /əʊ/",
        "lesson2",
        Color(0xFFdbf6ff)
    ),
    LessonCard(
        "Triphthongs",
        "Learn complex sounds formed by combining three vowel sounds in one syllable",
        "lesson3",
        Color(0xFFfff6eb)
    ),
    LessonCard(
        "Voiced Consonants",
        "Explore consonants made with vocal cord vibration like /b/, /d/, /z/",
        "lesson4",
        Color(0xFFe6f8f7)
    ),

    LessonCard(
        "Voiceless Consonants",
        "Understand consonants produced without vocal cord vibration like /p/, /t/, /f/",
        "lesson5",
        Color(0xFFf2f3f3)
    ),
    LessonCard(
        "Intonation",
        "Learn how pitch rises and falls affect meaning in English speech",
        "lesson6",
        Color(0xFFfbe8ef)
    ),
    LessonCard(
        "Stress",
        "Identify syllables that carry more emphasis in words and sentences",
        "lesson7",
        Color(0xFFfff0e6)
    ),
    LessonCard(
        "Rhythm",
        "Speak with the natural rhythm of English by mastering stress timing",
        "lesson8",
        Color(0xFFece7f6)
    )
)