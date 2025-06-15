package com.ziko.presentation.home

import androidx.compose.ui.graphics.Color
import com.ziko.data.remote.AssessmentCardInfo

// Network Status for Assessment Data
enum class AssessmentDataStatus {
    LOADING,        // Initial loading, no cached data
    CACHED,         // Showing cached data
    UPDATING,       // Updating cached data with fresh data
    UPDATED,        // Fresh data loaded successfully
    ERROR,          // Network error, showing cached data if available
    OFFLINE         // No internet connection
}

data class AssessmentDataState(
    val data: List<AssessmentCardInfo>,
    val status: AssessmentDataStatus,
    val lastUpdated: Long = 0L,
    val errorMessage: String? = null
)

// Extension function to get status color
fun AssessmentDataStatus.getStatusColor(): Color {
    return when (this) {
        AssessmentDataStatus.LOADING -> Color(0xFF9E9E9E)
        AssessmentDataStatus.CACHED -> Color(0xFFFFC107)
        AssessmentDataStatus.UPDATING -> Color(0xFFFFC107)
        AssessmentDataStatus.UPDATED -> Color(0xFF4CAF50)
        AssessmentDataStatus.ERROR -> Color(0xFFF44336)
        AssessmentDataStatus.OFFLINE -> Color(0xFFF44336)
    }
}

// Extension function to get status message
fun AssessmentDataStatus.getStatusMessage(): String {
    return when (this) {
        AssessmentDataStatus.LOADING -> "Loading assessment data..."
        AssessmentDataStatus.CACHED -> "Showing cached scores"
        AssessmentDataStatus.UPDATING -> "Updating scores..."
        AssessmentDataStatus.UPDATED -> "Scores up to date"
        AssessmentDataStatus.ERROR -> "Failed to update scores"
        AssessmentDataStatus.OFFLINE -> "No internet connection"
    }
}