package com.ziko.presentation.lesson

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.ui.model.LessonScreenContent
import com.ziko.util.AudioButtonWithLabel
import com.ziko.presentation.TopLessonBar

@Composable
fun LessonContent(
    content: LessonScreenContent,
    progress: Float, // 0f to 1f
    onCancel: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top App Bar with Cancel and Progress
        TopLessonBar(progress = progress, onCancel = onCancel)

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = content.mainText, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            AudioButtonWithLabel(text = "Listen", audioResId = content.audioResId)

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = content.subText, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(12.dp))

            content.options.forEach { option ->
                AudioButtonWithLabel(text = option.first, audioResId = option.second)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        }
    }
}
