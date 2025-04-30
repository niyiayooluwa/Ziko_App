package com.ziko.util

import android.media.MediaPlayer
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun AudioButtonWithLabel(text: String, @RawRes audioResId: Int) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                if (!isPlaying) {
                    // Start the audio and disable button
                    AudioManager.play(
                        context,
                        audioResId,
                        onStarted = { isPlaying = true },
                        onFinished = { isPlaying = false }
                    )
                }
            },
            enabled = !isPlaying // Disable button while playing
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "Play Audio",
                tint = if (isPlaying) Color.Gray else Color.White
            )
        }

        Text(text = text)
    }
}

