package com.ziko.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
    val purpleColor = Color(0xFF5E5AEC) // Purple color for top app bar

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Hello, $userName",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.White
                        )

                        Text(
                            text = "Track your pronunciation progress",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    // Profile picture placeholder
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        // You would replace this with an actual image if available
                        // For now, just a placeholder circle
                        Text(
                            text = userName.first().toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Scrollable content
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    // Increased bottom padding to account for the nav bar height + padding + shadow
                    .padding(bottom = 120.dp)
            ) {
                // Assessments Section
                Text(
                    text = "Your Assessments",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )

                // Placeholder content for assessment screen
                // You would replace this with your actual assessment UI
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Assessment content will go here",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
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