package com.ziko.presentation.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ziko.data.model.LessonDataProvider
import com.ziko.data.model.LessonIntroContentProvider
import com.ziko.navigation.Screen
import com.ziko.presentation.TopLessonBar
import com.ziko.ui.model.LessonIntroContent
import com.ziko.util.AudioButtonWithLabel

@Composable
fun LessonIntroScreen(
    navController: NavController,
    lessonId: String,
    onCancel: () -> Unit // Pass onCancel for the TopLessonBar
) {
    // --- Prepare content and progress ---
    val introContent: LessonIntroContent =
        LessonIntroContentProvider.getIntroContent(lessonId)
    // total screens = intro + repeating screens
    val totalScreens = 1 + LessonDataProvider.getLessonContent(lessonId).size
    val progress = 1f / totalScreens  // first screen = 1/total

    Column(modifier = Modifier.fillMaxSize()) {
        // --- Top App Bar with Cancel + Progress ---
        TopLessonBar(
            progress = progress,
            onCancel = onCancel // Pass it as onCancel
        )

        Spacer(Modifier.height(16.dp))

        // --- Body Content ---
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AudioButtonWithLabel(
                text = introContent.definitionText,
                audioResId = introContent.definitionAudio
            )

            introContent.points.forEach { point ->
                Text(text = point)
            }
        }

        Spacer(Modifier.weight(1f))

        // --- Start Lesson Button ---
        Button(
            onClick = { navController.navigate(Screen.LessonContent(lessonId).route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Start Lesson")
        }
    }
}

