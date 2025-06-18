package com.ziko.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziko.presentation.home.AssessmentDataStatus
import com.ziko.presentation.home.getStatusColor
import com.ziko.presentation.home.getStatusMessage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

/**
 * A composable status bar that shows the current network/data sync status
 * for the Assessment feature (e.g., loading, updated, cached, error, etc.).
 *
 * This bar slides into view only when the data is not in an UPDATED state
 * or when a cached timestamp should be shown.
 *
 * @param status The current data sync status represented by [AssessmentDataStatus].
 * @param lastUpdated Timestamp of the last successful data fetch (in millis).
 * @param onRefreshClick Callback triggered when user taps to retry in ERROR or OFFLINE state.
 * @param modifier Optional [Modifier] for styling or layout customization.
 */
@Composable
fun NetworkStatusIndicator(
    status: AssessmentDataStatus,
    lastUpdated: Long,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = status.getStatusColor()
    val statusMessage = status.getStatusMessage()

    val timeFormatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val lastUpdatedText = if (lastUpdated > 0) {
        "Updated ${timeFormatter.format(Date(lastUpdated))}"
    } else ""

    AnimatedVisibility(
        visible = status != AssessmentDataStatus.UPDATED || lastUpdatedText.isNotEmpty(),
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut(),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = status == AssessmentDataStatus.ERROR || status == AssessmentDataStatus.OFFLINE) {
                    onRefreshClick()
                },
            color = statusColor.copy(alpha = 0.1f),
            contentColor = statusColor
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Colored dot representing current status
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(statusColor, CircleShape)
                    )

                    Column {
                        // Status message (e.g., "Cached", "Offline", "Updating...")
                        Text(
                            text = statusMessage,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = statusColor
                        )

                        // Timestamp for last update (shown only in cached state)
                        if (lastUpdatedText.isNotEmpty() && status == AssessmentDataStatus.CACHED) {
                            Text(
                                text = lastUpdatedText,
                                fontSize = 10.sp,
                                color = statusColor.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                // Right-side indicator: loader, refresh button, or checkmark
                when (status) {
                    AssessmentDataStatus.LOADING, AssessmentDataStatus.UPDATING -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = statusColor
                        )
                    }
                    AssessmentDataStatus.ERROR, AssessmentDataStatus.OFFLINE -> {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Tap to retry",
                            modifier = Modifier.size(16.dp),
                            tint = statusColor
                        )
                    }
                    AssessmentDataStatus.UPDATED -> {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Up to date",
                            modifier = Modifier.size(16.dp),
                            tint = statusColor
                        )
                    }
                    AssessmentDataStatus.CACHED -> {
                        // No right icon for cached state
                    }
                }
            }
        }
    }
}
