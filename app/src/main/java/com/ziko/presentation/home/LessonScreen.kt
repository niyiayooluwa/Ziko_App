package com.ziko.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.ziko.ui.model.LessonCard
import com.ziko.navigation.Screen
import com.ziko.presentation.components.FloatingNavBar
import com.ziko.presentation.profile.UserViewModel
import com.ziko.core.util.UpdateSystemBarsColors

/**
 * LessonScreen is the main UI entry point for displaying all available lessons to the user.
 *
 * It shows:
 * - A greeting header with the user's profile picture or fallback initial.
 * - A scrollable list of [LessonCard]s the user can interact with.
 * - A floating navigation bar when appropriate (e.g., if current route is `Home` or `Assessment`).
 *
 * ## Responsibilities:
 * - Access user and profilePicUri state from [UserViewModel].
 * - Respond to nav route changes and adapt UI (e.g., show bottom nav).
 * - Navigate to [PreLessonLoading] or [LessonLoading] based on lesson ID logic.
 * - Maintain immersive UI through [UpdateSystemBarsColors].
 *
 * ## Design Decisions:
 * - The lesson list is static for now (from `val lessons = listOf(...)`), but could be moved to a ViewModel if made dynamic.
 * - Navigation logic for lesson1 is different to allow for an onboarding or tutorial-style lesson.
 *
 * @param navController Jetpack Navigation controller instance used for screen navigation.
 * @param userViewModel Shared ViewModel that exposes user and profile image info.
 */
@Composable
fun LessonScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    // --- System UI setup ---
    // Apply a custom color to the top and bottom system bars to match the lesson screen's brand colors.
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    // --- Reactive state ---
    // Observe user state and profile picture URI using Jetpack Compose's state observation.
    val user by userViewModel.user.collectAsState()
    val profilePicUri by userViewModel.profilePicUri.collectAsState()

    // Track the current navigation route to conditionally show the bottom bar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Scroll state for vertical scrolling of the lesson list
    val scrollState = rememberScrollState()
    val purpleColor = Color(0xFF410FA3)

    // Root container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // --- Header section with user info and greeting ---
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(purpleColor), // redundant but explicit
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
                    // Profile Image / Initial Fallback
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate(Screen.Profile.route) }
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (profilePicUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = profilePicUri),
                                contentDescription = "User profile picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            // Fallback: First letter of user’s first name
                            Text(
                                text = user?.first_name?.firstOrNull()?.toString() ?: "U",
                                fontSize = 13.sp,
                                color = Color(0xFF5b7bfe),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    // User greeting
                    Text(
                        text = "Hello, ${user?.first_name}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.White
                    )

                    // Motivational subtext
                    Text(
                        text = "What would you like to learn today?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }
            }

            // --- Scrollable Lesson List Section ---
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
                        bottom = 70.dp // Padding to avoid overlap with floating nav
                    )
            ) {
                Text(
                    text = "All Lessons",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    lessons.forEach { lesson ->
                        LessonCard(
                            title = lesson.title,
                            description = lesson.description,
                            color = lesson.color,
                            onClick = {
                                // lesson1 requires pre-loading screen before entering
                                if (lesson.id == "lesson1") {
                                    navController.navigate(Screen.PreLessonLoading(lesson.id).route)
                                } else {
                                    navController.navigate(Screen.LessonLoading(lesson.id).route)
                                }
                            }
                        )
                    }
                }
            }
        }

        // --- Floating bottom navigation bar ---
        // Shown only when the current route is either Home or Assessment screen
        if (currentRoute in listOf(Screen.Home.route, Screen.Assessment.route)) {
            Box(
                modifier = Modifier.fillMaxSize(),
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

/**
 * Renders a card representing a single lesson topic in the list.
 *
 * Designed to be minimal but clickable, [LessonCard] adapts its background color and handles
 * navigation via its `onClick` callback.
 *
 * ## Design:
 * - Uses [Card] with custom background color.
 * - Title is bold, description is faded.
 *
 * @param title The lesson's display title.
 * @param description A short summary of what the lesson covers.
 * @param color Background color to distinguish categories visually.
 * @param onClick Callback invoked when the card is tapped.
 */
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
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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

/**
 * A static list of predefined lessons shown in the [LessonScreen].
 *
 * Each lesson contains:
 * - A title (e.g., "Monophthongs")
 * - A brief description
 * - A unique ID (used for navigation)
 * - A background color for visual grouping
 *
 * This list is currently hardcoded but can be replaced with a dynamic ViewModel in the future.
 */
val lessons = listOf(
    LessonCard("Monophthongs", "Learn the 7 short & 5 long vowel sounds in English pronunciation", "lesson1", Color(0xFFf2f3f3)),
    LessonCard("Diphthongs", "Master gliding vowel sounds like /al/, /el/, and /əʊ/", "lesson2", Color(0xFFdbf6ff)),
    LessonCard("Triphthongs", "Learn complex sounds formed by combining three vowel sounds in one syllable", "lesson3", Color(0xFFfff6eb)),
    LessonCard("Voiced Consonants", "Explore consonants made with vocal cord vibration like /b/, /d/, /z/", "lesson4", Color(0xFFe6f8f7)),
    LessonCard("Voiceless Consonants", "Understand consonants produced without vocal cord vibration like /p/, /t/, /f/", "lesson5", Color(0xFFf2f3f3)),
    LessonCard("Intonation", "Learn how pitch rises and falls affect meaning in English speech", "lesson6", Color(0xFFfbe8ef)),
    LessonCard("Stress", "Identify syllables that carry more emphasis in words and sentences", "lesson7", Color(0xFFfff0e6)),
    LessonCard("Rhythm", "Speak with the natural rhythm of English by mastering stress timing", "lesson8", Color(0xFFece7f6))
)
