package com.ziko.presentation.components

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.ziko.util.SpeechManager

val color = Color(0xFF5b7bfe)

@Composable
fun SpeechButton(
    modifier: Modifier = Modifier,
    onSpeechResult: (String?) -> Unit,
    onPermissionDenied: () -> Unit
) {
    Log.d("SpeechButton", "Composing SpeechButton")

    val context = LocalContext.current
    val activity = context as? Activity

    // State variables
    val isListening = remember { mutableStateOf(false) }
    val rmsValue = remember { mutableFloatStateOf(0f) }
    val permissionGranted = remember { mutableStateOf(false) }
    val speechManager = remember { mutableStateOf<SpeechManager?>(null) }
    val initializationError = remember { mutableStateOf<String?>(null) }

    // Check permissions and initialize on first composition
    LaunchedEffect(Unit) {
        Log.d("SpeechButton", "LaunchedEffect started")

        try {
            // Check if permission is already granted
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED

            Log.d("SpeechButton", "Permission granted: $hasPermission")

            if (hasPermission) {
                permissionGranted.value = true

                // Only create SpeechManager if permission is granted
                Log.d("SpeechButton", "Creating SpeechManager")
                speechManager.value = SpeechManager(
                    context = context,
                    onRmsChangedCallback = { rms ->
                        Log.d("SpeechButton", "RMS changed: $rms")
                        rmsValue.floatValue = rms
                    },
                    onResultCallback = { result ->
                        Log.d("SpeechButton", "Speech result: $result")
                        isListening.value = false
                        onSpeechResult(result)
                    },
                    onListeningStateChangedCallback = { state ->
                        Log.d("SpeechButton", "Listening state changed: $state")
                        isListening.value = state
                    }
                )
                Log.d("SpeechButton", "SpeechManager created successfully")
            } else {
                // Request permission
                permissionGranted.value = false
                if (activity != null) {
                    Log.d("SpeechButton", "Requesting permission")
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.RECORD_AUDIO),
                        1
                    )
                } else {
                    Log.e("SpeechButton", "Activity is null, cannot request permission")
                    onPermissionDenied()
                }
            }
        } catch (e: Exception) {
            Log.e("SpeechButton", "Error in LaunchedEffect", e)
            initializationError.value = e.message
        }
    }

    // Cleanup when component is disposed
    DisposableEffect(Unit) {
        onDispose {
            Log.d("SpeechButton", "Disposing SpeechButton")
            try {
                speechManager.value?.destroy()
            } catch (e: Exception) {
                Log.e("SpeechButton", "Error destroying SpeechManager", e)
            }
        }
    }

    // UI
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
            .border(1.dp, color, RoundedCornerShape(12.dp))
            .clickable(
                enabled = permissionGranted.value &&
                        !isListening.value &&
                        initializationError.value == null
            ) {
                Log.d("SpeechButton", "Button clicked, starting listening")
                try {
                    speechManager.value?.startListening()
                } catch (e: Exception) {
                    Log.e("SpeechButton", "Error starting listening", e)
                    initializationError.value = e.message
                }
            }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            // Show error if initialization failed
            initializationError.value != null -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Mic",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Microphone Error",
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }
            }
            // Show listening animation
            isListening.value -> {
                Log.d("SpeechButton", "Showing listening animation")
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(21) { index ->
                        BarShape(rms = rmsValue.floatValue, index = index)
                    }
                }
            }
            // Show idle state
            else -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Mic",
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (permissionGranted.value) "Tap to Talk" else "Permission Required",
                        color = color,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }
}

@Composable
fun BarShape(rms: Float, index: Int) {
    // Calculate height with bounds checking
    val baseHeight = 2f
    val maxHeight = 10f
    val variation = (index % 4) * 2f
    val targetHeightFloat = (rms + variation).coerceIn(baseHeight, maxHeight)
    val targetHeight = (targetHeightFloat * 10).dp

    val animatedHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(durationMillis = 150),
        label = "rmsBar"
    )

    Box(
        modifier = Modifier
            .width(7.dp)
            .height(animatedHeight)
            .clip(RoundedCornerShape(50))
            .background(color)
    )
}