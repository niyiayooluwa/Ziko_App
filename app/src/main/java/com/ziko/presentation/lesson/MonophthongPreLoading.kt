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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ziko.R
import com.ziko.data.model.LessonDataProvider
import com.ziko.navigation.Screen
import com.ziko.core.util.UpdateSystemBarsColors

/**
 * A composable screen shown before the monophthongs lesson begins.
 *
 * Acts as a soft onboarding prompt with a learning tip and motivational image,
 * helping users prepare their environment (e.g., volume/headphones).
 *
 * Navigation Flow:
 * - Returns to Home if back button is pressed.
 * - Proceeds to the actual lesson loading screen when the "Got it!" button is clicked.
 *
 * @param navController Used for navigating between screens.
 * @param lessonId ID of the current lesson used to retrieve lesson-specific info.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreLoadingScreen(
    navController: NavController,
    lessonId: String,
) {
    // Update system bars (status and navigation) to match lesson theme
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    // Retrieve the lesson info once using the lessonId
    val lesson = remember(lessonId) {
        LessonDataProvider.getLessonInfo(lessonId)
    }

    Scaffold(
        topBar = {
            // Centered Top App Bar with back navigation
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF410fa3),
                ),
                title = {
                    if (lesson != null) {
                        Text(
                            text = "", // Intentionally empty for this screen
                            fontSize = 22.sp,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate to home on back press
                        navController.navigate(Screen.Home.route)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                ),
        ) {
            // --- Center Section (Image + Learning Tip Text) ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    painter = painterResource(R.drawable.mono_preloading),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )

                Spacer(Modifier.height(30.dp))

                Text(
                    text = "Learning Tip",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF080e1e),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Hear it loud and clear : turn up the\nvolume or use headphone.",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF656872)
                )
            }

            // --- Bottom CTA Button ---
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF5B7BFE))
                    .clickable {
                        // Navigate to the next screen in the lesson flow
                        navController.navigate(Screen.LessonLoading(lessonId).route)
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Got it!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}
