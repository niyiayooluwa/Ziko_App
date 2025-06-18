package com.ziko.presentation.assessment

import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
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
import com.ziko.core.util.AudioManager
import com.ziko.core.util.UpdateSystemBarsColors
import com.ziko.core.util.normalizeText

/**
 * Displays the main content of the assessment flow, including the top progress bar and the
 * current assessment screen (either a multiple choice or speaking assessment).
 *
 * This composable manages screen transitions, assessment results, and the current progress state.
 *
 * @param content The type of assessment content to show (e.g., MCQ or Speaking).
 * @param progress The current progress of the assessment as a float between 0 and 1.
 * @param onCancel Called when the user cancels the assessment.
 * @param onContinue Called when the user completes a screen and proceeds to the next.
 * @param onResult Callback indicating whether the user got the current assessment item correct.
 * @param currentScreen The current screen index being shown (1-based).
 * @param totalScreens The total number of assessment screens.
 * @param onNavigateBack Called when navigating back (usually for internal nav control).
 * @param isFirstScreen True if the current screen is the first screen.
 * @param onStart Called only once when the assessment starts.
 * @param lessonId The ID of the lesson associated with this assessment (used for result tracking, etc).
 */
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
    // Ensures onStart is only triggered once when the screen is first composed
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
                showBackNavigation = false // disables system back navigation by default
            )
        }
    ) { paddingValues ->
        // Decide which type of assessment screen to show based on content type
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


/**
 * Displays a speaking assessment screen where the user listens to a prompt and speaks the expected response.
 *
 * Handles mic permission, speech recognition, result evaluation, and single-attempt gating logic.
 * Also manages lifecycle-aware audio behavior and custom navigation bar feedback based on correctness.
 *
 * @param content The specific assessment content containing the expected spoken text, audio path, and display text.
 * @param onContinue Callback to advance to the next screen after assessment.
 * @param onResult Callback providing the result of the speech evaluation (true if correct, false otherwise).
 * @param paddingValues Padding values passed from the scaffold to avoid UI clipping.
 * @param lessonId The ID of the current lesson (used to adjust UI conditionally).
 */
@Composable
fun SpeakAssessmentUI(
    content: AssessmentScreenContent.SpeakAssessment,
    onContinue: () -> Unit,
    onResult: (Boolean) -> Unit,
    paddingValues: PaddingValues,
    lessonId: String
) {//
    val expectedText = content.expectedText

    // Controller manages button state: idle, recording, completed, etc.
    val speechButtonController = rememberSpeechButtonController()

    // State: spoken transcript
    val spokenText = remember { mutableStateOf("") }

    // State: evaluation result (null = not yet evaluated, true = pass, false = fail)
    val speechCondition = remember { mutableStateOf<Boolean?>(null) }

    // Flag to check if any audio has been recorded at all
    val hasRecordedSpeech = remember { mutableStateOf(false) }

    // Attempt tracking
    val attemptCount = remember { mutableIntStateOf(0) }
    val maxAttempts = 1 // Assessment only allows a single evaluation

    // State for mic permission denial
    var permissionDenied by remember { mutableStateOf(false) }

    // Lifecycle observer for releasing audio focus when screen is destroyed
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(AudioManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(AudioManager)
        }
    }

    // Reset state on new question prompt
    LaunchedEffect(expectedText) {
        spokenText.value = ""
        speechCondition.value = null
        hasRecordedSpeech.value = false
        attemptCount.intValue = 0
        speechButtonController.enable() // Enable button again after retry
    }

    // Bottom nav bar feedback based on result
    val navBarColor = when (speechCondition.value) {
        true -> Color(0xFF12D18E)
        false -> Color(0xFFf75555)
        null -> Color.White // Neutral state
    }

    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = navBarColor
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ─── Top Section: Instructions & Display Text ───────────────────────────────
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // 📘 Instruction text
                Text(
                    text = content.instructions,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xFF080E1E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Optional playback card
                if (content.audioPath != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 66.dp)
                            .fillMaxWidth()
                            .dashedRoundedBorder(
                                color = Color(0xFF656872),
                                strokeWidth = 1.dp,
                                dashLength = 8.dp,
                                gapLength = 4.dp,
                                cornerRadius = 12.dp
                            ),
                    ) {
                        Text(
                            text = content.displayText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            color = Color(0xFF080E1E),
                            textAlign = if (lessonId == "lesson8") TextAlign.Center else TextAlign.Start,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // buffer for bottom content
        }

        // ─── Bottom Section: Mic Input + Evaluation ────────────────────────────────
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Permission info
                if (permissionDenied) {
                    Text(
                        text = "Please enable microphone permission in settings",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Can't speak now",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF656872)
                )

                Spacer(modifier = Modifier.height(11.dp))

                // Speech Input Button
                SpeechButton(
                    modifier = Modifier,
                    onSpeechResult = { result ->
                        spokenText.value = result ?: ""
                        hasRecordedSpeech.value = !result.isNullOrBlank()
                        speechCondition.value = null // Reset state for fresh evaluation

                        if (!result.isNullOrBlank()) {
                            speechButtonController.setCompleted() // Visual feedback
                        }
                    },
                    onPermissionDenied = {
                        permissionDenied = true
                    },
                    controller = speechButtonController
                )
            }

            Spacer(Modifier.height(12.dp))

            // Pass/Fail/Retry logic
            SuccessIndicator(
                modifier = Modifier.fillMaxWidth(),
                condition = speechCondition.value,
                hasRecorded = hasRecordedSpeech.value,
                attemptCount = attemptCount.intValue,
                maxAttempts = maxAttempts,
                onClick = {
                    when (speechCondition.value) {
                        null -> {
                            if (hasRecordedSpeech.value) {
                                // Normalize and evaluate
                                val normalizedSpoken = normalizeText(spokenText.value)
                                val normalizedExpected = normalizeText(expectedText)
                                val isCorrect = normalizedSpoken == normalizedExpected

                                attemptCount.intValue += 1
                                speechCondition.value = isCorrect
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
}


/**
 * Displays an MCQ (Multiple Choice Question) assessment screen.
 *
 * Users are shown a question and a list of selectable options. The selected option is evaluated
 * for correctness and fed into a bottom "SuccessIndicator" bar. Only one attempt is allowed.
 *
 * Color of the system navigation bar changes based on result state.
 *
 * @param content The MCQ content containing instructions, the question, correct answer, and options.
 * @param onContinue Callback invoked to move to the next screen after evaluation.
 * @param onResult Callback returning the result of the evaluation (true = correct).
 * @param paddingValues Scaffold padding to avoid UI overlap.
 */
@Composable
fun McqAssessmentUI(
    content: AssessmentScreenContent.McqAssessment,
    onContinue: () -> Unit,
    onResult: (Boolean) -> Unit,
    paddingValues: PaddingValues,
) {
    // Result state: null = not yet answered, true = correct, false = incorrect
    val isAnswerCorrect = remember { mutableStateOf<Boolean?>(null) }

    // Ground truth answer
    val correctAnswer = content.correctAnswer

    // Currently selected option
    var selectedOption by remember { mutableStateOf("") }

    // True after any option has been selected
    val hasAnswered = remember { mutableStateOf(false) }

    // Set bottom navigation color based on answer result
    val navBarColor = when (isAnswerCorrect.value) {
        true -> Color(0xFF12D18E)   // Green
        false -> Color(0xFFf75555)  // Red
        null -> Color.White         // Neutral
    }

    // Dynamically update nav bar when answer state changes
    UpdateSystemBarsColors(
        topColor = Color(0xFF410FA3),
        bottomColor = navBarColor
    )

    // Reset component state when a new question is loaded
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
        // ─── Main Section: Instructions, Question, Options ──────────────────────────────
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                // Instruction text
                Text(
                    text = content.instructions,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xFF080E1E),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Question inside a bordered card
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

                // Options list
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
                            onClick = {
                                if (isAnswerCorrect.value == null) {
                                    selectedOption = option
                                    hasAnswered.value = true
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // To avoid overlapping the bottom bar
        }

        // ─── Bottom Section: Answer Evaluation & Progression ─────────────────────────────
        SuccessIndicator(
            modifier = Modifier.fillMaxWidth(),
            condition = isAnswerCorrect.value,
            hasRecorded = hasAnswered.value, // Always true once user taps an option
            attemptCount = 1, // Always one attempt
            maxAttempts = 1,
            onClick = {
                if (isAnswerCorrect.value == null) {
                    if (selectedOption.isNotBlank()) {
                        // Evaluate selected option
                        val isCorrect = selectedOption == correctAnswer
                        isAnswerCorrect.value = isCorrect
                        hasAnswered.value = true
                    }
                } else {
                    // Move forward after evaluation
                    onResult(isAnswerCorrect.value == true)
                    onContinue()
                }
            }
        )
    }
}

/**
 * Adds a dashed, rounded border to the composable using native canvas drawing.
 *
 * This uses `drawBehind` to manually draw a stroked rounded rectangle
 * with dash and gap effects. Ideal for emphasizing content blocks like
 * questions or highlights.
 *
 * @param color The color of the dashed border.
 * @param strokeWidth Width of the border stroke.
 * @param dashLength Length of each dash segment.
 * @param gapLength Space between dashes.
 * @param cornerRadius Radius of the corners.
 * @return A modifier with the dashed rounded border applied.
 */
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

        val paint = Paint().apply {
            style = Paint.Style.STROKE
            this.color = color.toArgb()
            this.strokeWidth = strokePx
            pathEffect = DashPathEffect(floatArrayOf(dashPx, gapPx), 0f)
            isAntiAlias = true
        }

        // Account for stroke thickness to avoid clipping
        val rect = RectF(
            strokePx / 2f,
            strokePx / 2f,
            size.width - strokePx / 2f,
            size.height - strokePx / 2f
        )

        drawContext.canvas.nativeCanvas.drawRoundRect(rect, radiusPx, radiusPx, paint)
    }
)


/**
 * A reusable row component for displaying selectable MCQ options.
 *
 * Shows a selectable card with a check icon and text. Visuals change based
 * on selection state. Can be disabled via the [enabled] flag.
 *
 * @param text The text to display in the option.
 * @param selected Whether this option is currently selected.
 * @param onClick Callback when the row is clicked.
 * @param modifier Modifier for additional layout control.
 * @param enabled Whether the row is interactable. Defaults to true.
 */
@Composable
fun CustomSelectableRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // Dynamic styling based on selection state
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




