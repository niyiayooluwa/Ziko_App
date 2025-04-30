package com.ziko.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ziko.navigation.Screen
import com.ziko.presentation.components.BottomNavBar

@Composable
fun LessonScreen(
    navController: NavController,
    userName: String = "Sam" // Default or from ViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        bottomBar = {
            if (currentRoute in listOf(Screen.Home.route, Screen.Assessment.route)) {
                BottomNavBar(navController = navController, currentRoute = currentRoute ?: "")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Header Section
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Hello, $userName",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Black
                    )

                    Text(
                        text = "What would you like to learn today?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF656872),
                        modifier = Modifier.padding(top = 8.dp))
                }

                // All Lessons Section
                Text(
                    text = "All Lessons",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Lessons List
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    lessons.forEach { lesson ->
                        LessonCard(
                            title = lesson.title,
                            description = lesson.description,
                            onClick = {
                                val lessonId = lesson.id
                                navController.navigate(Screen.LessonLoading(lessonId).route)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LessonCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
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

// Keep your existing data classes
data class LessonCard(
    val title: String,
    val description: String,
    val id: String
)

val lessons = listOf(
    LessonCard("Monophthongs", "Learn the 7 short & 5 long vowel sounds in English pronunciation", "lesson1"),
    LessonCard("Diphthongs", "Master gliding vowel sounds like /al/, /el/, and /əʊ/", "lesson2"),
    LessonCard("Triphthongs", "Learn complex sounds formed by combining three vowel sounds in one syllable", "lesson3"),
    LessonCard("Voiced Consonants", "Explore consonants made with vocal cord vibration like /b/, /d/, /z/", "lesson4"),
    LessonCard("Voiceless Consonants", "Understand consonants produced without vocal cord vibration like /p/, /t/, /f/", "lesson5"),
    LessonCard("Intonation", "Learn how pitch rises and falls affect meaning in English speech", "lesson6"),
    LessonCard("Stress", "Identify syllables that carry more emphasis in words and sentences", "lesson7"),
    LessonCard("Rhythm", "Speak with the natural rhythm of English by mastering stress timing", "lesson8")
)