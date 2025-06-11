package com.ziko.presentation.assessment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ziko.presentation.components.ProgressTopAppBar
import com.ziko.presentation.components.SpeechButton
import com.ziko.presentation.components.SuccessIndicator
import com.ziko.presentation.components.rememberSpeechButtonController
import com.ziko.ui.model.AssessmentScreenContent
import com.ziko.util.AudioManager
import com.ziko.util.normalizeText

@Composable
fun AssessmentContent(
    content: AssessmentScreenContent,
    progress: Float,
    onCancel: () -> Unit,
    onContinue: () -> Unit,
    onResult: (Boolean) -> Unit,
    currentScreen: Int,
    totalScreens: Int,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean,
    onStart: () -> Unit,
    lessonId: String
) {
    // Trigger onStart only once on first composition
    LaunchedEffect(Unit) { onStart() }

    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            ProgressTopAppBar(
                progress = progress,
                currentScreen = currentScreen,
                totalScreens = totalScreens,
                onCancel = onCancel,
                onNavigateBack = onNavigateBack,
                isFirstScreen = isFirstScreen,
                showBackNavigation = false
            )
        }
    ) { paddingValues ->
        when (content) {
            is AssessmentScreenContent.SpeakAssessment -> SpeakAssessmentUI(
                content = content,
                onContinue = onContinue,
                onResult = onResult,
                paddingValues = paddingValues,
                lessonId = lessonId
            )
            is AssessmentScreenContent.McqAssessment -> McqAssessmentUI(
                content = content,
                onContinue = onContinue,
                onResult = onResult,
                paddingValues = paddingValues,
            )
        }
    }
}

@Composable
fun SpeakAssessmentUI(
    content: AssessmentScreenContent.SpeakAssessment,
    onContinue: () -> Unit,
    onResult: (Boolean) -> Unit,
    paddingValues: PaddingValues,
    lessonId: String
) {
    val expectedText = content.expectedText
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    val speechButtonController = rememberSpeechButtonController()

    // Speech recognition state
    val spokenText = remember { mutableStateOf("") }
    val speechCondition = remember { mutableStateOf<Boolean?>(null) }
    val hasRecordedSpeech = remember { mutableStateOf(false) }
    val attemptCount = remember { mutableIntStateOf(0) }
    val maxAttempts = 1 // Single attempt for assessment
    var permissionDenied by remember { mutableStateOf(false) }

    // AudioManager lifecycle management
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(AudioManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(AudioManager)
        }
    }

    // Reset speech state when expected text changes
    LaunchedEffect(expectedText) {
        spokenText.value = ""
        speechCondition.value = null
        hasRecordedSpeech.value = false
        attemptCount.intValue = 0
        speechButtonController.enable()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Main content area
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Instructions and audio section
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Instruction text
                Text(
                    text = content.instructions,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xFF080E1E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Audio playback button if sound is available
                if (content.audioPath != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 66.dp)
                            .clickable {
                                if (!isPlaying) {
                                    AudioManager.playAsset(
                                        context,
                                        assetPath = content.audioPath,
                                        onStarted = { isPlaying = true },
                                        onFinished = { isPlaying = false }
                                    )
                                }
                            }
                            .fillMaxWidth()
                            .dashedRoundedBorder(
                                color = Color(0xFF656872),
                                strokeWidth = 1.dp,
                                dashLength = 8.dp,
                                gapLength = 4.dp,
                                cornerRadius = 12.dp
                            ),
                    ) {
                        if (lessonId != "lesson8") {
                            Text(
                                text = content.displayText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                color = Color(0xFF080E1E),
                                modifier = Modifier.padding(16.dp)
                            )
                        } else {
                            Text(
                                text = content.displayText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                color = Color(0xFF080E1E),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
            // Bottom spacing to avoid UI overlap
            Spacer(modifier = Modifier.height(80.dp))
        }

        // Bottom evaluation bar
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Speech input section
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Permission denied message
                if (permissionDenied) {
                    Text(
                        text = "Please enable microphone permission in settings",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Helper text
                Text(
                    text = "Can't speak now",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF656872)
                )

                Spacer(modifier = Modifier.height(11.dp))

                // Speech input button
                SpeechButton(
                    modifier = Modifier,
                    onSpeechResult = { result ->
                        spokenText.value = result ?: ""
                        hasRecordedSpeech.value = !result.isNullOrBlank()
                        speechCondition.value = null // Reset evaluation state
                    },
                    onPermissionDenied = {
                        permissionDenied = true
                    },
                    controller = speechButtonController
                )
            }

            Spacer(Modifier.height(24.dp))
            SuccessIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                condition = speechCondition.value,
                hasRecorded = hasRecordedSpeech.value,
                attemptCount = attemptCount.intValue,
                maxAttempts = maxAttempts,
                onClick = {
                    when (speechCondition.value) {
                        null -> {
                            if (hasRecordedSpeech.value) {
                                val normalizedSpoken = normalizeText(spokenText.value)
                                val normalizedExpected = normalizeText(expectedText)
                                val isCorrect = normalizedSpoken == normalizedExpected

                                attemptCount.intValue += 1
                                speechCondition.value = isCorrect

                                speechButtonController.disable() // ✅ Disable right after evaluating
                            }
                        }

                        true -> {
                            speechButtonController.disable() // ✅ Already correct, disable just in case
                            onResult(true)
                            onContinue()
                        }

                        false -> {
                            speechButtonController.disable() // ✅ Wrong, but still done — disable
                            onResult(false)
                            onContinue()
                        }
                    }
                }

            )
        }
    }
}

@Composable
fun McqAssessmentUI(
    content: AssessmentScreenContent.McqAssessment,
    onContinue: () -> Unit,
    onResult: (Boolean) -> Unit,
    paddingValues: PaddingValues,
) {
    val isAnswerCorrect = remember { mutableStateOf<Boolean?>(null) }
    val correctAnswer = content.correctAnswer
    var selectedOption by remember { mutableStateOf("")}
    val hasAnswered = remember { mutableStateOf(false) }

    LaunchedEffect(correctAnswer) {
        isAnswerCorrect.value = null
        hasAnswered.value = false
        selectedOption = ""
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        // Main content area
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
                //.align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Question section
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = content.instructions,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xFF080E1E),
                )

                Spacer(modifier = Modifier.height(16.dp))

                //Question
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .defaultMinSize(minHeight = 66.dp)
                        .fillMaxWidth()
                        .dashedRoundedBorder(
                            color = Color(0xFF656872),
                            strokeWidth = 1.dp,
                            dashLength = 8.dp,
                            gapLength = 4.dp,
                            cornerRadius = 12.dp
                        )
                ) {
                    Text(
                        text = content.question,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF080E1E),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Options section
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    content.options.forEach { option ->
                        val isSelected = selectedOption == option

                        CustomSelectableRow(
                            modifier = Modifier.fillMaxWidth(),
                            text = option,
                            selected = isSelected,
                            enabled = !hasAnswered.value,
                            onClick = {
                                if (!hasAnswered.value) {
                                    selectedOption = option
                                    hasAnswered.value = true
                                    //isAnswerCorrect.value = option == content.correctAnswer
                                }
                            }
                        )
                    }
                }
            }

            // Bottom spacing to avoid UI overlap
            Spacer(modifier = Modifier.height(80.dp))
        }

        // Bottom evaluation bar
        SuccessIndicator(
            modifier = Modifier.fillMaxWidth(),
            condition = isAnswerCorrect.value,
            hasRecorded = hasAnswered.value, // Always true for MCQ since they selected an option
            attemptCount = 1,
            maxAttempts = 1,
            onClick = {
                when (isAnswerCorrect.value) {
                    null -> {
                        if (hasAnswered.value) {
                            val isCorrect = selectedOption == correctAnswer
                            isAnswerCorrect.value = isCorrect
                        }
                    }
                    true -> {
                        onResult(true)
                        onContinue()
                    }
                    false -> {
                        onResult(false)
                        onContinue()
                    }
                }
            }
        )
    }
}

fun Modifier.dashedRoundedBorder(
    color: Color = Color.Black,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 5.dp,
    cornerRadius: Dp = 12.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = strokeWidth.toPx()
        val dashPx = dashLength.toPx()
        val gapPx = gapLength.toPx()
        val radiusPx = cornerRadius.toPx()

        val paint = android.graphics.Paint().apply {
            style = android.graphics.Paint.Style.STROKE
            this.color = color.toArgb()
            this.strokeWidth = strokePx
            pathEffect = android.graphics.DashPathEffect(floatArrayOf(dashPx, gapPx), 0f)
            isAntiAlias = true
        }

        val rect = android.graphics.RectF(
            strokePx / 2f,
            strokePx / 2f,
            size.width - strokePx / 2f,
            size.height - strokePx / 2f
        )

        drawContext.canvas.nativeCanvas.drawRoundRect(rect, radiusPx, radiusPx, paint)
    }
)

@Composable
fun CustomSelectableRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val borderColor = if (selected) Color(0xFF5b7bfe) else Color(0xFFe5e5e5)
    val backgroundColor = if (selected) Color(0xFFdbf6ff) else Color.White
    val icon = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.Circle
    val iconTint = if (selected) Color(0xFF5b7bfe) else Color(0xFFE5E5E5)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 74.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (selected) "Selected" else "Not selected",
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}



