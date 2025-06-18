package com.ziko.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable UI component that indicates the user's attempt success/failure and displays
 * a contextual action button ("Check", "Try again", "Next", or "Skip to Next").
 *
 * This is often used after speech or form-based interactions to give visual feedback and
 * prompt the next logical step.
 *
 * @param condition Determines the result of the attempt:
 * - `true` → success
 * - `false` → failure
 * - `null` → awaiting evaluation
 * @param hasRecorded Whether the user has made an attempt (e.g., recorded speech) — only relevant when [condition] is null.
 * @param attemptCount Number of current attempts made.
 * @param maxAttempts Maximum allowed attempts before the user is prompted to skip.
 * @param onClick Callback for when the bottom button is clicked (e.g., proceed, retry, check).
 * @param modifier Modifier to be applied to the whole component.
 */
@Composable
fun SuccessIndicator(
    condition: Boolean?,
    hasRecorded: Boolean,
    attemptCount: Int,
    maxAttempts: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Whether the action button should be clickable
    val isButtonEnabled = when (condition) {
        null -> hasRecorded       // Only enable "Check" if recording exists
        true, false -> true       // Always enable "Next", "Try again", or "Skip"
    }

    // Background color of the action button based on status
    val buttonBackgroundColor = when {
        !isButtonEnabled -> Color(0xFFE0E0E0)       // Disabled = grey
        condition != null -> Color.White            // Success or failure = white
        else -> Color(0xFF5b7bfe)                   // Active check = primary blue
    }

    // Text color of the action button
    val buttonTextColor = when {
        !isButtonEnabled -> Color(0xFF9E9E9E)       // Disabled text = grey
        condition == true -> Color(0xFF12D18E)       // Success = green
        condition == false -> Color(0xFFf75555)      // Error = red
        else -> Color.White                          // Default = white
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .background(
                // Card background color changes based on result
                when (condition) {
                    true -> Color(0xFF12D18E)    // Green for success
                    false -> Color(0xFFf75555)   // Red for failure
                    null -> Color.White          // Default white for neutral
                }
            )
            .padding(
                top = 24.dp,
                bottom = 40.dp,
                start = 24.dp,
                end = 8.dp,
            ),
    ) {
        // Top row: icon + status message
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Conditionally show success/fail icon
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

            // Conditionally show corresponding message
            when (condition) {
                true -> "You nailed it!" // Success
                false -> {
                    // If max attempts reached, show fallback message
                    if (attemptCount >= maxAttempts) {
                        "Don't worry, let's move on."
                    } else {
                        "Whoops! Not quite yet." // Retry available
                    }
                }
                null -> null // No message until user attempts
            }?.let {
                Text(
                    text = it,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.White
                )
            }
        }

        // Bottom action button
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
                    true -> "Next" // Successful, proceed
                    false -> {
                        if (attemptCount >= maxAttempts) {
                            "Skip to Next" // Failed too many times
                        } else {
                            "Try again" // Failed, can retry
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
