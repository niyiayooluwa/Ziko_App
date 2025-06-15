package com.ziko.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuccessIndicator(
    condition: Boolean?,
    hasRecorded: Boolean, // To track if user has recorded
    attemptCount: Int,    // Current attempt number
    maxAttempts: Int,     // Maximum attempts allowed
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Determine if button should be enabled
    val isButtonEnabled = when (condition) {
        null -> hasRecorded // Only enabled if user has recorded something
        true -> true        // Always enabled when correct
        false -> true       // Always enabled when incorrect (for retry or skip)
    }

    // Determine button colors based on state
    val buttonBackgroundColor = when {
        !isButtonEnabled -> Color(0xFFE0E0E0) // Grey when disabled
        condition == true -> Color.White
        condition == false -> Color.White
        else -> Color(0xFF5b7bfe) // Blue when ready to check
    }

    val buttonTextColor = when {
        !isButtonEnabled -> Color(0xFF9E9E9E) // Grey text when disabled
        condition == true -> Color(0xFF12D18E)
        condition == false -> Color(0xFFf75555)
        else -> Color.White
    }

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .background(
                when (condition) {
                    true -> Color(0xFF12D18E)
                    false -> Color(0xFFf75555)
                    null -> Color.White
                }
            )
            .padding(
                top = 24.dp,
                bottom = 40.dp,
                start = 24.dp,
                end = 8.dp,
            ),
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (condition) {
                true -> Icons.Filled.CheckCircle
                false -> Icons.Filled.Cancel
                null -> null
            }?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            when (condition) {
                true -> "You nailed it!"
                false -> {
                    if (attemptCount >= maxAttempts) {
                        "Don't worry, let's move on."
                    } else {
                        "Whoops! Not quite yet."
                    }
                }
                null -> null
            }?.let {
                Text(
                    text = it,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.White
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(buttonBackgroundColor)
                .clickable(enabled = isButtonEnabled) {
                    onClick()
                }
        ) {
            Text(
                text = when (condition) {
                    true -> "Next"
                    false -> {
                        if (attemptCount >= maxAttempts) {
                            "Skip to Next"
                        } else {
                            "Try again"
                        }
                    }
                    null -> {
                        if (hasRecorded) "Check" else "Record first"
                    }
                },
                color = buttonTextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500
            )
        }
    }
}
