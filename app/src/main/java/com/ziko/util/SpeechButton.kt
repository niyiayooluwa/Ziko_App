package com.ziko.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun SpeechButton(
    modifier: Modifier = Modifier,
    onSpeechResult: (String?) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isListening = remember { mutableStateOf(false) }
    val rmsValue = remember { mutableFloatStateOf(0f) }
    val permissionGranted = remember { mutableStateOf(false) }
    val speechManager = remember {
        mutableStateOf<SpeechManager?>(null)
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_GRANTED
        ) {
            permissionGranted.value = true
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
            permissionGranted.value = false
            onPermissionDenied()
        }

        speechManager.value = SpeechManager(
            context = context,
            onRmsChanged = { rms -> rmsValue.floatValue = rms },
            onResult = { result -> onSpeechResult(result) },
            onListeningStateChanged = { state -> isListening.value = state }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            speechManager.value?.destroy()
        }
    }

    Box(
        modifier = modifier
            .size(width = 300.dp, height = 150.dp)
            .border(2.dp, Color.Blue, RoundedCornerShape(12.dp))
            .clickable(enabled = permissionGranted.value && !isListening.value) {
                speechManager.value?.startListening()
            }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isListening.value) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Mic",
                    tint = Color.Blue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tap to Speak",
                    color = Color.Blue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(12) { index ->
                    BarShape(rms = rmsValue.floatValue, index = index)
                }
            }
        }
    }
}


@Composable
fun BarShape(rms: Float, index: Int) {
    val animatedHeight by animateDpAsState(
        targetValue = ((rms + (index % 4) * 2f).coerceIn(2f, 10f) * 10).dp,
        animationSpec = tween(durationMillis = 150),
        label = "rmsBar"
    )

    Box(
        modifier = Modifier
            .width(8.dp)
            .height(animatedHeight)
            .clip(RoundedCornerShape(50))
            .background(Color.Blue)
    )
}


