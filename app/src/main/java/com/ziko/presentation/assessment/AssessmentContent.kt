package com.ziko.presentation.assessment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ziko.presentation.components.SuccessIndicator
import com.ziko.ui.model.AssessmentScreenContent

@Composable
fun AssessmentContent(
    content: AssessmentScreenContent,
    onResult: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = content.instructions,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        when (content) {
            is AssessmentScreenContent.SpeakAssessment -> SpeakAssessmentUI(content, onResult)
            is AssessmentScreenContent.McqAssessment -> McqAssessmentUI(content, onResult)
        }
    }
}

@Composable
fun SpeakAssessmentUI(
    content: AssessmentScreenContent.SpeakAssessment,
    onResult: (Boolean) -> Unit
) {
    Column {
        Text(
            text = "Say: \"${content.instructions}\"",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (content.audioPath != null) {
            // Optional playback (if you include audio example)
            AudioPlayerComponent(audioPath = content.audioPath)
        }

        Spacer(modifier = Modifier.height(16.dp))

        SpeakButton(onSpeechCaptured = { spokenText ->
            val correct = spokenText.equals(content.expectedText, ignoreCase = true)
            onResult(correct)
        })

        Spacer(modifier = Modifier.height(20.dp))

        SuccessIndicator() // Show result if needed externally
    }
}

@Composable
fun McqAssessmentUI(
    content: AssessmentScreenContent.McqAssessment,
    onResult: (Boolean) -> Unit
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }

    Text(
        text = content.question,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    content.options.forEach { option ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !hasAnswered) {
                    selectedOption = option
                    hasAnswered = true
                    onResult(option == content.correctAnswer)
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == option,
                onClick = null // Handled above
            )
            Text(text = option)
        }
    }

    if (hasAnswered) {
        Spacer(modifier = Modifier.height(16.dp))
        SuccessIndicator(isCorrect = selectedOption == content.correctAnswer)
    }
}

