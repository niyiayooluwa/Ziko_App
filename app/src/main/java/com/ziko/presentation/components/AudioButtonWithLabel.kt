package com.ziko.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.ziko.core.util.AudioManager

/**
 * Enum representing the size configuration for the audio buttons and labels.
 *
 * Used to dynamically scale the button size and text styling based on screen context.
 */
enum class Size {
    SMALL, BIG
}

/**
 * Renders a horizontal audio button and a label text side-by-side.
 *
 * Plays or pauses an audio file located at [assetPath] using [AudioManager].
 * The button icon reflects play/pause state.
 *
 * @param text The label shown next to the button.
 * @param assetPath The internal asset path to the audio file to play.
 * @param size The visual size (icon + font) of the component.
 */
@Composable
fun AudioButtonWithLabel(text: String, assetPath: String, size: Size) {
    val context = LocalContext.current

    // Tracks the currently playing asset
    val currentlyPlaying by AudioManager.currentlyPlaying.collectAsState()
    val isPlaying = currentlyPlaying == assetPath

    // UI configuration based on size
    val buttonModifier = if (size == Size.SMALL) Modifier.size(32.dp) else Modifier.size(48.dp)
    val fontSize = if (size == Size.SMALL) 20.sp else 28.sp
    val fontWeight = if (size == Size.SMALL) FontWeight.Medium else FontWeight.SemiBold

    // Determine appropriate icon
    val icon = if (isPlaying) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            onClick = {
                if (!isPlaying) {
                    AudioManager.playAsset(context, assetPath)
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
                contentDescription = if (isPlaying) "Pause Audio" else "Play Audio"
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

/**
 * A vertically-stacked variant of [AudioButtonWithLabel] used in lesson 8 UI.
 *
 * Icon appears on top of the label text. Same logic for playing audio applies.
 *
 * @param text Label text under the button.
 * @param assetPath Path to the audio asset.
 * @param size Size config for icon and font.
 */
@Composable
fun AudioButtonWithLabelForLesson8(text: String, assetPath: String, size: Size) {
    val context = LocalContext.current
    val currentlyPlaying by AudioManager.currentlyPlaying.collectAsState()
    val isPlaying = currentlyPlaying == assetPath

    val buttonModifier = if (size == Size.SMALL) Modifier.size(32.dp) else Modifier.size(48.dp)
    val fontSize = 20.sp
    val fontWeight = if (size == Size.SMALL) FontWeight.Medium else FontWeight.SemiBold
    val icon = if (isPlaying) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            onClick = {
                if (!isPlaying) {
                    AudioManager.playAsset(context, assetPath)
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
                contentDescription = if (isPlaying) "Pause Audio" else "Play Audio"
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

/**
 * An inline horizontal audio button with split-styled text.
 *
 * This is used in introductory screens where part of the sentence is styled as a call-to-action.
 *
 * @param textOne The first segment, styled with underline and blue.
 * @param textTwo The second segment, styled as normal black text.
 * @param assetPath Path to the audio asset.
 */
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
                    AudioManager.playAsset(context = context, assetPath = assetPath)
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

        // Combine styled and normal text
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
