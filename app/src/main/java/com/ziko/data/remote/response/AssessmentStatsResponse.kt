package com.ziko.data.remote.response

import com.ziko.data.remote.model.AssessmentStatsItem

/**
 * File: AssessmentStatsResponse.kt
 * Layer: data (remote)
 * Description: Represents the top-level structure of the response received from the remote server
 *              for a request to fetch assessment statistics. This response includes either a list
 *              of stats, an optional success message, or an error message.
 *
 * This file resides in the `data.remote.response` layer, as it directly reflects server responses and is part
 * of the app's data access layer.
 *
 * Data class representing the HTTP response body for assessment statistics retrieval.
 *
 * This class models the structure of the JSON response returned by the backend API when fetching
 * user performance data across various lessons or assessments. It includes a message for success
 * cases, an error message for failures, and a list of assessment stat items in either case.
 *
 * @property msg A human-readable success message returned by the backend (nullable).
 * @property errorMsg An error message returned if the request failed (nullable).
 * @property data A list of [AssessmentStatsItem] representing individual lesson statistics.
 *
 * Usage Example:
 * ```
 * val response = api.getAssessmentStats()
 * if (response.errorMsg != null) {
 *     showError(response.errorMsg)
 * } else {
 *     displayStats(response.data)
 * }
 * ```
 *
 * @see AssessmentStatsItem
 */
data class AssessmentStatsResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: List<AssessmentStatsItem>
)
