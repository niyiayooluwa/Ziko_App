package com.ziko.presentation.home

import androidx.compose.ui.graphics.Color
import com.ziko.domain.model.AssessmentCardInfo

/**
 * Represents the state of the assessment data fetch operation,
 * including loading, cached, error, and success states.
 */
enum class AssessmentDataStatus {
    /**
     * No data yet, and currently loading from network.
     */
    LOADING,

    /**
     * Displaying cached data, while possibly trying to update in the background.
     */
    CACHED,

    /**
     * Currently fetching fresh data but already showing cached content.
     */
    UPDATING,

    /**
     * Fresh, successfully loaded data being displayed.
     */
    UPDATED,

    /**
     * Network request failed — fallback to cached data if available.
     */
    ERROR,

    /**
     * No internet connection detected — unable to fetch or update.
     */
    OFFLINE
}

/**
 * UI-facing state holder representing the assessment data and its network status.
 *
 * @param data A list of [AssessmentCardInfo] representing the assessments to be displayed.
 * @param status The current [AssessmentDataStatus] representing network/data load state.
 * @param lastUpdated Epoch timestamp of the last successful update (used for "updated X mins ago" logic).
 * @param errorMessage Optional human-readable error message (can be shown in a snackbar or toast).
 */
data class AssessmentDataState(
    val data: List<AssessmentCardInfo>,
    val status: AssessmentDataStatus,
    val lastUpdated: Long = 0L,
    val errorMessage: String? = null
)

/**
 * Returns the appropriate color for the given [AssessmentDataStatus], intended for
 * use in badges, icons, or status indicators.
 *
 * - Grey for loading
 * - Amber for cached/updating
 * - Green for updated
 * - Red for error/offline
 */
fun AssessmentDataStatus.getStatusColor(): Color {
    return when (this) {
        AssessmentDataStatus.LOADING -> Color(0xFF9E9E9E)     // Grey
        AssessmentDataStatus.CACHED -> Color(0xFFFFC107)      // Amber
        AssessmentDataStatus.UPDATING -> Color(0xFFFFC107)    // Amber
        AssessmentDataStatus.UPDATED -> Color(0xFF4CAF50)     // Green
        AssessmentDataStatus.ERROR -> Color(0xFFF44336)       // Red
        AssessmentDataStatus.OFFLINE -> Color(0xFFF44336)     // Red
    }
}

/**
 * Returns a user-facing status message for the given [AssessmentDataStatus],
 * which is displayed under the topAppBar in the Assessments screen.
 */
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
