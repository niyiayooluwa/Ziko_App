package com.ziko.presentation.practice

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentLoadingScreen(
    lessonId: String,
    onProgress: () -> Unit
) {
    val textPart = lessonId.takeWhile { it.isLetter() }.replaceFirstChar { it.uppercase() }
    val numPart = lessonId.takeLastWhile { it.isDigit() }
    val lessonIdentifier = "$textPart $numPart"

    val progress = remember { Animatable(0.02f) }

    LaunchedEffect(Unit) {
        // Animate progress from 0 to 1 over 3 seconds
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000) // 3 seconds
        )
        delay(40) // wait 1 more second
        onProgress()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF410fa3),
                ),
                title = { Text(text = "") },
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center).padding(bottom = 50.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.practice_loading_illustration),
                        contentDescription = "Lesson Illustration",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(278.dp)
                            .width(240.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Loading...",
                        fontSize = 28.sp,
                        color = Color(0xFF080E1E),
                        fontWeight = FontWeight.ExtraBold
                    )

                    LinearProgressIndicator(
                        progress = progress.value,
                        color = Color(0xFF5B7BFE),
                        trackColor = Color(0xFFe5e5e5),
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .height(11.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        text = "You have completed $lessonIdentifier.You are ready\nfor a bigger challenge.",
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF656872)
                    )
                }
            }
        }
    }
}