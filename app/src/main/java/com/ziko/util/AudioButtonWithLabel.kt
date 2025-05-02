package com.ziko.util

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ziko.R

enum class Size{
    SMALL, BIG
}

@Composable
fun AudioButtonWithLabel(text: String, @RawRes audioResId: Int, size: Size) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    when (size) {
        Size.SMALL -> Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFF5B7BFE),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(32.dp),
                //enabled = !isPlaying // Disable button while playing
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Play Audio",
                )
            }

            Text(
                text = text,
                color = Color(0xFF080e1e),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Size.BIG -> Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFF5B7BFE),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(48.dp),
                //enabled = !isPlaying // Disable button while playing
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Play Audio",
                )
            }

            Text(
                text = text,
                color = Color(0xFF080E1E),
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AudioButtonWithLabelForiIntro(
    textOne: String,
    textTwo: String,
    @RawRes audioResId: Int
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFF5B7BFE),
                contentColor = Color.White
            ),
            modifier = Modifier.size(32.dp),
            enabled = !isPlaying // Disable button while playing
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "Play Audio",
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
        )
    }
}

@Composable
fun AudioButtonWithWrappedText(
    textOne: String,
    textTwo: String,
    @RawRes audioResId: Int
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    AndroidView(
        factory = { ctx ->
            TextView(ctx).apply {
                textSize = 18f
                setLineSpacing(0f, 1.2f)
                setTextColor(Color.Black.toArgb())
                typeface = Typeface.DEFAULT

                // Set up compound drawable (icon on the left)
                val icon = ContextCompat.getDrawable(ctx, R.drawable.volume)
                setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                compoundDrawablePadding = 16

                // Format the text using SpannableString
                val fullText = textOne + textTwo
                val spannable = SpannableString(fullText)

                spannable.setSpan(
                    ForegroundColorSpan(Color(0xFF5B7BFE).toArgb()),
                    0, textOne.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    UnderlineSpan(),
                    0, textOne.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                spannable.setSpan(
                    ForegroundColorSpan(Color.Black.toArgb()),
                    textOne.length, fullText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                setText(spannable, TextView.BufferType.SPANNABLE)

                // Make the icon trigger audio
                setOnTouchListener { _, event ->
                    val iconBounds = compoundDrawables[0]?.bounds
                    if (event.x < (iconBounds?.width() ?: 0) + compoundDrawablePadding) {
                        if (!isPlaying) {
                            AudioManager.play(
                                ctx,
                                audioResId,
                                onStarted = { isPlaying = true },
                                onFinished = { isPlaying = false }
                            )
                        }
                        true
                    } else {
                        false
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}



