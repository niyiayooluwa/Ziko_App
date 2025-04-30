package com.ziko.presentation.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LessonCompletionScreen(
    onContinuePractice: () -> Unit,
    onBackToHome: () -> Unit,
    lessonId: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Lesson Completed",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Congratulations message
        Text(
            text = "You have completed lesson $lessonId. You are ready for a bigger challenge.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Continue button
        Button(
            onClick = onContinuePractice,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = "Continue to practice exercise", fontSize = 16.sp)
        }

        // Back to home button
        TextButton(
            onClick = onBackToHome,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Back to home",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}