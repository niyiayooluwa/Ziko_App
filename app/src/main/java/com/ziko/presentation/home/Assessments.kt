package com.ziko.presentation.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.ziko.R
import com.ziko.navigation.Screen
import com.ziko.presentation.components.FloatingNavBar
import com.ziko.presentation.components.NetworkStatusIndicator
import com.ziko.presentation.profile.UserViewModel
import com.ziko.util.UpdateSystemBarsColors

@Composable
fun AssessmentScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )
    val assessmentStatsViewModel: AssessmentStatsViewModel = hiltViewModel()
    val assessmentDataState by assessmentStatsViewModel.assessmentDataState.collectAsState()
    val assessmentStats = assessmentDataState.data

    val user by userViewModel.user.collectAsState()
    val profilePicUri by userViewModel.profilePicUri.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scrollState = rememberScrollState()
    val purpleColor = Color(0xFF410FA3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = purpleColor
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 36.dp, bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate(Screen.Profile.route) }
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF5b7bfe).copy(alpha = 0.2f))
                    ) {
                        if (profilePicUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = profilePicUri),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            Text(
                                text = user?.first_name?.first().toString(),
                                fontSize = 13.sp,
                                color = Color(0xFF5b7bfe),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Text(
                        "Assessment",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.White
                    )

                    Text(
                        text = "Test your knowledge and sharpen your\npronunciation skills",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Network Status Indicator
            NetworkStatusIndicator(
                status = assessmentDataState.status,
                lastUpdated = assessmentDataState.lastUpdated,
                onRefreshClick = { assessmentStatsViewModel.refreshData() }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 70.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    SpecialAssessment(
                        title = "General Assessment",
                        description = "A mix of questions from all completed lessons",
                        highestScore = 90,
                        accuracy = 56,
                        backGroundColor = Color(0xFFFFF6EB),
                        borderColor = Color(0xFFFFF195),
                        darkerColor = Color(0xFFB78107),
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                    SpecialAssessment(
                        title = "Target Practice",
                        description = "Work on the lessons where your score is below 70%",
                        highestScore = 90,
                        accuracy = 56,
                        backGroundColor = Color(0xFFDBF6FF),
                        borderColor = Color(0xFFD0EAF4),
                        onClick = {},
                        darkerColor = Color(0xFF00516E),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }

                Text(
                    "Drill by Topic",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black
                )

                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    if (assessmentStats.isEmpty() && assessmentDataState.status == AssessmentDataStatus.LOADING) {
                        // Show loading state for individual cards
                        repeat(8) {
                            AssessmentCardSkeleton()
                        }
                    } else {
                        assessmentStats.forEach { assessment ->
                            AssessmentCard(
                                title = assessment.title,
                                highestScore = assessment.highestScore,
                                accuracy = assessment.accuracy,
                                onClick = {
                                    val lessonId = assessment.id
                                    navController.navigate(Screen.AssessmentLoading(lessonId).route)
                                }
                            )
                        }
                    }
                }
            }
        }

        if (currentRoute in listOf(Screen.Home.route, Screen.Assessment.route)) {
            Box(
                modifier = Modifier.fillMaxSize(),
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
private fun AssessmentCardSkeleton() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(16.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp))
                )
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(12.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp))
                )
            }

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(24.dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            )
        }
    }
}

@Composable
fun AssessmentCard(
    title: String,
    highestScore: Int? = null,
    accuracy: Int? = null,
    onClick: () -> Unit
) {
    val colorPrimary = when (accuracy) {
        null -> Color(0xFF656872)
        in 70..100 -> Color(0xFF5BA890)
        in 50..69 -> Color(0xFFF76400)
        else -> Color(0xFFD6185D)
    }
    val colorSecondary = when (accuracy) {
        null -> Color(0xFFf3f3f4)
        in 0..49 -> Color(0xFFf7d1df)
        in 50..69 -> Color(0xFFf7d1df)
        else -> Color(0xFFDeeee9)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(title, fontSize = 17.sp, fontWeight = FontWeight.W500, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = highestScore?.let { "Highest Score: $it%" }
                        ?: "Highest Score: Not attempted",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF656872)
                )
            }

            Box(modifier = Modifier.size(72.dp)) {
                if (highestScore != null && accuracy != null) {
                    val animatedProgress by animateFloatAsState(
                        targetValue = accuracy.toFloat() / 100,
                        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
                        label = "",
                    )
                    CircularProgressIndicator(
                        progress = { animatedProgress},
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        color = colorPrimary,
                        strokeWidth = 8.43.dp,
                        trackColor = colorSecondary,
                        strokeCap = StrokeCap.Round
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            "$accuracy%",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorPrimary
                        )
                        Text(
                            "Accuracy",
                            fontSize = 8.64.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF656872)
                        )
                    }
                } else {
                    CircularProgressIndicator(
                        progress = { 0.5f },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        color = Color(0xFFe5e5e5),
                        strokeWidth = 8.43.dp,
                        trackColor = Color(0xFFececec),
                        strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            "N/A",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFe5e5e5)
                        )
                        Text(
                            "Accuracy",
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

@Composable
fun SpecialAssessment(
    title: String,
    description: String,
    highestScore: Int?,
    accuracy: Int?,
    backGroundColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    darkerColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .clickable(onClick = onClick)
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(backGroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(size = 16.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 11.sp,
                fontWeight = FontWeight.W400,
                color = Color(0xFF656872)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Icon(
                        painter = painterResource(R.drawable.highest_score),
                        contentDescription = "High score",
                        tint = Color(0xFF656872),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "$highestScore%",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = when (highestScore) {
                            null -> Color(0xFF656872)
                            in 70..100 -> Color(0xFF5BA890)
                            in 50..69 -> Color(0xFFF76400)
                            else -> Color(0xFFD6185D)
                        }
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Icon(
                        painter = painterResource(R.drawable.accuracy),
                        contentDescription = "accuracy",
                        tint = Color(0xFF656872),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "$accuracy%",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = when (accuracy) {
                            null -> Color(0xFF656872)
                            in 70..100 -> Color(0xFF5BA890)
                            in 50..69 -> Color(0xFFF76400)
                            else -> Color(0xFFD6185D)
                        }
                    )
                }

            }

            Box (
                modifier = Modifier
                    .clip(CircleShape)
                    .background(darkerColor)
                    .size(40.dp)
                    .clickable{onClick()},
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "button",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}



