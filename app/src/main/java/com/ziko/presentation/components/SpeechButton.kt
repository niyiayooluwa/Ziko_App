package com.ziko.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.data.EmptyGroup.data
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ziko.util.SpeechManager
import kotlinx.coroutines.delay
import kotlin.random.Random

val color = Color(0xFF5b7bfe)

// Enhanced controller with completed state
class SpeechButtonController {
    var isEnabled by mutableStateOf(true)
        private set

    var isCompleted by mutableStateOf(false)
        private set

    fun disable() {
        isEnabled = false
    }

    fun enable() {
        isEnabled = true
        isCompleted = false
    }

    fun setCompleted() {
        isEnabled = false
        isCompleted = true
    }
}

@Composable
fun SpeechButton(
    modifier: Modifier = Modifier,
    controller: SpeechButtonController,
    onSpeechResult: (String?) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val isListening = remember { mutableStateOf(false) }
    val rmsValue = remember { mutableFloatStateOf(0f) }
    val speechManager = remember { mutableStateOf<SpeechManager?>(null) }
    val initializationError = remember { mutableStateOf<String?>(null) }
    val showSettingsPrompt = remember { mutableStateOf(false) }
    val showNoSpeechError = remember { mutableStateOf(false) }

    val permissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted.value = granted
        if (!granted) {
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity ?: return@rememberLauncherForActivityResult,
                Manifest.permission.RECORD_AUDIO
            )
            if (!shouldShowRationale) {
                showSettingsPrompt.value = true
            } else {
                onPermissionDenied()
            }
        }
    }

    // Clear the no speech error after a delay
    LaunchedEffect(showNoSpeechError.value) {
        if (showNoSpeechError.value) {
            delay(3000) // Show error for 3 seconds
            showNoSpeechError.value = false
        }
    }

    LaunchedEffect(Unit) {
        if (!permissionGranted.value) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    LaunchedEffect(permissionGranted.value) {
        if (permissionGranted.value && speechManager.value == null) {
            try {
                speechManager.value = SpeechManager(
                    context = context,
                    onRmsChangedCallback = { rms ->
                        rmsValue.floatValue = rms
                    },
                    onResultCallback = { result ->
                        isListening.value = false
                        if (result == null) {
                            showNoSpeechError.value = true
                        }
                        onSpeechResult(result)
                    },
                    onListeningStateChangedCallback = { state ->
                        isListening.value = state
                        if (state) {
                            // Clear any previous error when starting to listen
                            showNoSpeechError.value = false
                        }
                    }
                )
            } catch (e: Exception) {
                initializationError.value = e.message
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                speechManager.value?.destroy()
            } catch (_: Exception) {}
        }
    }

    val color = Color(0xFF5b7bfe)
    val errorColor = Color(0xFFFF6B6B)

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .border(
                    1.dp,
                    if (showNoSpeechError.value) errorColor else color,
                    RoundedCornerShape(12.dp)
                )
                .clickable(
                    enabled = controller.isEnabled &&
                            permissionGranted.value &&
                            !isListening.value &&
                            initializationError.value == null &&
                            !controller.isCompleted
                ) {
                    try {
                        speechManager.value?.startListening()
                    } catch (e: Exception) {
                        initializationError.value = e.message
                    }
                }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
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

                // Show static waveform when completed
                controller.isCompleted -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val staticSeed = remember { Random.nextInt() }
                        repeat(21) { index ->
                            StaticBarShape(index = index, seed = staticSeed)
                        }
                    }
                }

                isListening.value -> {
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

                showNoSpeechError.value -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MicOff,
                            contentDescription = "Mic Off",
                            tint = errorColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Oops, didn't catch that",
                            color = errorColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                    }
                }

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
                            text = when {
                                permissionGranted.value -> "Tap to Talk"
                                showSettingsPrompt.value -> "Open Settings"
                                else -> "Permission Required"
                            },
                            color = color,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                    }
                }
            }
        }

        if (showSettingsPrompt.value) {
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
            ) {
                Text("Open App Settings", color = color)
            }
        }
    }
}

@Composable
fun StaticBarShape(index: Int, seed: Int) {
    val color = Color(0xFF5b7bfe)

    // Generate consistent random heights per render using a seed
    val heights = remember(seed) {
        List(21) { Random(seed + it).nextInt(1, 10).toFloat() }
    }

    val targetHeight = (heights[index % heights.size] * 10).dp

    Box(
        modifier = Modifier
            .width(7.dp)
            .height(targetHeight)
            .clip(RoundedCornerShape(50))
            .background(color)
    )
}

@Composable
fun BarShape(rms: Float, index: Int) {
    val color = Color(0xFF5b7bfe)

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

@Composable
fun rememberSpeechButtonController(): SpeechButtonController {
    return remember { SpeechButtonController() }
}