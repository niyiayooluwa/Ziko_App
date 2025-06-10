package com.ziko.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.util.AudioManager

enum class Size {
    SMALL, BIG
}

//In a row
@Composable
fun AudioButtonWithLabel(text: String, assetPath: String, size: Size) {
    val context = LocalContext.current
    val currentlyPlaying by AudioManager.currentlyPlaying.collectAsState()

    val isPlaying = currentlyPlaying == assetPath

    val buttonModifier = when (size) {
        Size.SMALL -> Modifier.size(32.dp)
        Size.BIG -> Modifier.size(48.dp)
    }

    val fontSize = when (size) {
        Size.SMALL -> 20.sp
        Size.BIG -> 28.sp
    }

    val fontWeight = when (size) {
        Size.SMALL -> FontWeight.Medium
        Size.BIG -> FontWeight.SemiBold
    }

    val icon = if (isPlaying) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            onClick = {
                if (!isPlaying) {
                    AudioManager.playAsset(
                        context,
                        assetPath,
                        onStarted = {},
                        onFinished = {}
                    )
                } else {
                    AudioManager.stop()
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFF5B7BFE),
                contentColor = Color.White
            ),
            modifier = buttonModifier
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Play Audio"
            )
        }

        Text(
            text = text,
            color = Color(0xFF080E1E),
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun AudioButtonWithLabelForLesson8(text: String, assetPath: String, size: Size) {
    val context = LocalContext.current
    val currentlyPlaying by AudioManager.currentlyPlaying.collectAsState()

    val isPlaying = currentlyPlaying == assetPath

    val buttonModifier = when (size) {
        Size.SMALL -> Modifier.size(32.dp)
        Size.BIG -> Modifier.size(48.dp)
    }

    val fontSize = when (size) {
        Size.SMALL -> 20.sp
        Size.BIG -> 28.sp
    }

    val fontWeight = when (size) {
        Size.SMALL -> FontWeight.Medium
        Size.BIG -> FontWeight.SemiBold
    }

    val icon = if (isPlaying) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            onClick = {
                if (!isPlaying) {
                    AudioManager.playAsset(
                        context,
                        assetPath,
                        onStarted = {},
                        onFinished = {}
                    )
                } else {
                    AudioManager.stop()
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFF5B7BFE),
                contentColor = Color.White
            ),
            modifier = buttonModifier
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Play Audio"
            )
        }

        Text(
            text = text,
            color = Color(0xFF080E1E),
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AudioButtonWithLabelForIntro(
    textOne: String,
    textTwo: String,
    assetPath: String
) {
    val context = LocalContext.current
    val currentlyPlaying by AudioManager.currentlyPlaying.collectAsState()
    val isPlaying = currentlyPlaying == assetPath

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (!isPlaying) {
                    Log.d("AudioButton", "Clicked: trying to play $assetPath")
                    AudioManager.playAsset(
                        context = context,
                        assetPath = assetPath
                    )
                } else {
                    AudioManager.stop()
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFF5B7BFE),
                contentColor = Color.White
            ),
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = if (isPlaying) "Stop Audio" else "Play Audio"
            )
        }

        val styledText = buildAnnotatedString {
            append(textOne)
            addStyle(
                style = SpanStyle(
                    color = Color(0xFF5B7BFE),
                    textDecoration = TextDecoration.Underline
                ),
                start = 0,
                end = textOne.length
            )
            append(textTwo)
            addStyle(
                style = SpanStyle(
                    color = Color.Black,
                    textDecoration = TextDecoration.None
                ),
                start = textOne.length,
                end = textOne.length + textTwo.length
            )
        }

        Text(
            text = styledText,
            fontSize = 22.sp,
            softWrap = true,
        )
    }
}
