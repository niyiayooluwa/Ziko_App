package com.ziko.presentation.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ziko.presentation.components.ProgressTopAppBar
import com.ziko.ui.model.LessonScreenContent
import com.ziko.presentation.components.AudioButtonWithLabel
import com.ziko.presentation.components.Size
import com.ziko.util.AudioManager
import com.ziko.util.UpdateSystemBarsColors

@Composable
fun LessonContent(
    content: LessonScreenContent,
    progress: Float, // 0f to 1f
    onCancel: () -> Unit,
    onContinue: () -> Unit,
    currentScreen: Int,
    totalScreens: Int,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean,
) {
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(AudioManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(AudioManager)
        }
    }

    Scaffold(
        topBar = {
            // Using the custom progress top app bar with the cancel button
            ProgressTopAppBar(
                progress = progress,
                currentScreen = currentScreen,
                totalScreens = totalScreens,
                onCancel = onCancel,
                onNavigateBack = onNavigateBack,
                isFirstScreen = isFirstScreen
            )
        }
    ) { paddingValues ->
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content.mainText,
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (content.sound != null) {
                AudioButtonWithLabel(
                    text = content.sound.first,
                    assetPath = content.sound.second,
                    size = Size.BIG
                )

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                content.boldText?.let {
                    Text(
                        text = it,
                        color = Color(0xFF080E1E),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            content.subText?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF080E1E)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Column (
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                content.options.forEach { option ->
                    AudioButtonWithLabel(
                        text = option.first,
                        assetPath = option.second,
                        size = Size.SMALL
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF5B7BFE))
                    .clickable { onContinue() }
            ) {
                Text(
                    text ="Continue",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}