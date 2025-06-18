package com.ziko.data.remote.response

/**
 * File: ScoreUpdateResponse.kt
 * Layer: data → remote → response
 *
 * Represents the server's response after submitting or updating a user's assessment score.
 * Includes status information and an optional [ScoreUpdateData] payload detailing score metrics.
 *
 * @property msg Optional success message returned by the server (e.g., "Score updated successfully").
 * @property errorMsg Optional error message in case the update failed.
 * @property data Optional [ScoreUpdateData] object containing updated accuracy and highest score values.
 *
 * Example JSON response:
 * ```
 * {
 *   "msg": "Score updated successfully",
 *   "errorMsg": null,
 *   "data": {
 *     "accuracy": 93,
 *     "highest_score": 87
 *   }
 * }
 * ```
 */
data class ScoreUpdateResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: ScoreUpdateData?
)

/**
 * Contains detailed score metrics returned from a score update request.
 * Typically used to update UI or local cache with the user's latest performance stats.
 *
 * @property accuracy The latest accuracy percentage (0–100) for the given assessment.
 * @property highest_score The highest score the user has achieved in that assessment.
 */
data class ScoreUpdateData(
    val accuracy: Int,
    val highest_score: Int
)

