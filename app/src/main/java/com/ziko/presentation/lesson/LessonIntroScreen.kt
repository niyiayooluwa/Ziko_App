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
import androidx.navigation.NavController
import com.ziko.data.model.LessonDataProvider
import com.ziko.data.model.LessonIntroContentProvider
import com.ziko.navigation.Screen
import com.ziko.presentation.components.ProgressTopAppBar
import com.ziko.ui.model.LessonIntroContent
import com.ziko.presentation.components.AudioButtonWithLabelForIntro
import com.ziko.util.AudioManager
import com.ziko.util.UpdateSystemBarsColors

@Composable
fun LessonIntroScreen(
    navController: NavController,
    lessonId: String,
    onCancel: () -> Unit,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean
) {
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = Color.White
    )

    val introContent: LessonIntroContent =
        LessonIntroContentProvider.getIntroContent(lessonId)
    val totalScreens = 1 + LessonDataProvider.getLessonContent(lessonId).size
    val progress = 1f / totalScreens
    val currentScreen = 1

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

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            Column (
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.Start
            ){
                AudioButtonWithLabelForIntro(
                    textOne = introContent.definitionTextOne,
                    textTwo = introContent.definitionTextTwo,
                    assetPath = introContent.definitionAudio
                )
            }

            introContent.points?.forEach { point ->
                if (point != null) {
                    Text(
                        text = point,
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp,
                        color = Color(0xFF080e1e)
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // --- Start Lesson Button ---
            /*{Button(
                onClick = { navController.navigate(Screen.LessonContent(lessonId).route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Continue")
            }}*/

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF5B7BFE))
                    .clickable{ navController.navigate(Screen.LessonContent(lessonId).route) }
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
