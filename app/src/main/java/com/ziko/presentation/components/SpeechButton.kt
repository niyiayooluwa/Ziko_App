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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
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
import com.ziko.core.speech.SpeechManager
import kotlinx.coroutines.delay
import kotlin.random.Random

private val color = Color(0xFF5b7bfe)

/**
 * Controller class used to manage state for the [SpeechButton].
 * Allows enabling/disabling the button and marking it as "completed".
 */
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

/**
 * Composable microphone button that listens for audio input using [SpeechManager].
 * Displays a waveform when listening and handles permission and error states.
 *
 * @param modifier Modifier to apply to the root container.
 * @param controller Controller object used to enable/disable or mark speech input as completed.
 * @param onSpeechResult Callback triggered when speech recognition completes with text (or null if failed).
 * @param onPermissionDenied Callback triggered when the RECORD_AUDIO permission is denied and rationale is required.
 */
@Composable
fun SpeechButton(
    modifier: Modifier = Modifier,
    controller: SpeechButtonController,
    onSpeechResult: (String?) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    // State holders for internal control
    val isListening = remember { mutableStateOf(false) }
    val rmsValue = remember { mutableFloatStateOf(0f) }
    val speechManager = remember { mutableStateOf<SpeechManager?>(null) }
    val initializationError = remember { mutableStateOf<String?>(null) }
    val showSettingsPrompt = remember { mutableStateOf(false) }
    val showNoSpeechError = remember { mutableStateOf(false) }

    val permissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher for microphone permission
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

    // Auto-clear no-speech error after 3s
    LaunchedEffect(showNoSpeechError.value) {
        if (showNoSpeechError.value) {
            delay(3000)
            showNoSpeechError.value = false
        }
    }

    // Initial permission request
    LaunchedEffect(Unit) {
        if (!permissionGranted.value) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    // Initialize SpeechManager once permission is granted
    LaunchedEffect(permissionGranted.value) {
        if (permissionGranted.value && speechManager.value == null) {
            try {
                speechManager.value = SpeechManager(
                    context = context,
                    onRmsChangedCallback = { rms -> rmsValue.floatValue = rms },
                    onResultCallback = { result ->
                        isListening.value = false
                        if (result == null) showNoSpeechError.value = true
                        onSpeechResult(result)
                    },
                    onListeningStateChangedCallback = { state ->
                        isListening.value = state
                        if (state) showNoSpeechError.value = false
                    }
                )
            } catch (e: Exception) {
                initializationError.value = e.message
            }
        }
    }

    // Cleanup SpeechManager
    DisposableEffect(Unit) {
        onDispose {
            try {
                speechManager.value?.destroy()
            } catch (_: Exception) {}
        }
    }

    val errorColor = Color(0xFFFF6B6B)

    Column(modifier = modifier) {
        // Main mic button UI
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
                    // Initialization error
                    MicRow("Microphone Error", Color.Red, Icons.Default.Mic)
                }

                controller.isCompleted -> {
                    // Show frozen waveform after recording
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val staticSeed = remember { Random.nextInt() }
                        repeat(21) { index -> StaticBarShape(index, staticSeed) }
                    }
                }

                isListening.value -> {
                    // Live waveform while listening
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
                    // No speech detected
                    MicRow("Oops, didn't catch that", errorColor, Icons.Default.MicOff)
                }

                else -> {
                    // Default state
                    val label = when {
                        permissionGranted.value -> "Tap to Talk"
                        showSettingsPrompt.value -> "Open Settings"
                        else -> "Permission Required"
                    }
                    MicRow(label, color, Icons.Default.Mic)
                }
            }
        }

        // Open settings if permission permanently denied
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

/**
 * Reusable row that displays a microphone icon and text label.
 */
@Composable
private fun MicRow(text: String, tint: Color, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = tint, fontSize = 20.sp, fontWeight = FontWeight.W500)
    }
}

/**
 * Displays a static waveform bar with pseudo-random height, based on a seed.
 * Used when speech input is completed.
 */
@Composable
fun StaticBarShape(index: Int, seed: Int) {
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

/**
 * Displays a single animated bar based on current [rms] level.
 * Used to create dynamic waveform UI during speech input.
 */
@Composable
fun BarShape(rms: Float, index: Int) {
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

/**
 * Remembers and returns a [SpeechButtonController] scoped to the composition.
 */
@Composable
fun rememberSpeechButtonController(): SpeechButtonController {
    return remember { SpeechButtonController() }
}
