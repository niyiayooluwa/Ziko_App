package com.ziko.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziko.presentation.home.AssessmentDataStatus

// For NetworkStatusIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ziko.presentation.home.getStatusColor
import com.ziko.presentation.home.getStatusMessage
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NetworkStatusIndicator(
    status: AssessmentDataStatus,
    lastUpdated: Long,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = status.getStatusColor()
    val statusMessage = status.getStatusMessage()

    // Format last updated time
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
                    // Status indicator dot
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(statusColor, CircleShape)
                    )

                    Column {
                        Text(
                            text = statusMessage,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = statusColor
                        )

                        if (lastUpdatedText.isNotEmpty() && status == AssessmentDataStatus.CACHED) {
                            Text(
                                text = lastUpdatedText,
                                fontSize = 10.sp,
                                color = statusColor.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                // Show loading indicator or refresh icon
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
                    AssessmentDataStatus.CACHED -> {}
                }
            }
        }
    }
}