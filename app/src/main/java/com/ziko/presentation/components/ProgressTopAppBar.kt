package com.ziko.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A top app bar designed for multi-step processes (e.g. assessments, onboarding),
 * which shows a linear progress indicator, current step, and optional back/cancel buttons.
 *
 * @param progress The current progress value between 0f and 1f.
 * @param currentScreen The index of the current screen (e.g. step 2).
 * @param totalScreens The total number of screens/steps in the flow.
 * @param onCancel Callback triggered when the cancel icon is tapped.
 * @param onNavigateBack Callback triggered when the back icon is tapped.
 * @param isFirstScreen Whether the current screen is the first step (affects icon semantics).
 * @param showBackNavigation If false, hides the back navigation icon entirely.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressTopAppBar(
    progress: Float,
    currentScreen: Int,
    totalScreens: Int,
    onCancel: () -> Unit,
    onNavigateBack: () -> Unit,
    isFirstScreen: Boolean,
    showBackNavigation: Boolean? = true
) {
    CenterAlignedTopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .width(240.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = Color(0xFF5B7BFE),
                    trackColor = Color(0xFFE5E5E5),
                    strokeCap = StrokeCap.Round
                )

                // Step indicator (e.g. "2/5")
                Text(
                    text = "$currentScreen/$totalScreens",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500
                )
            }
        },
        navigationIcon = {
            if (showBackNavigation == true) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = if (isFirstScreen) "Cancel" else "Back",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onCancel) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF410FA3) // Purple background
        )
    )
}
