package com.ziko.data.remote

import com.ziko.data.model.AssessmentStatsItem

data class AssessmentStatsResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: List<AssessmentStatsItem>
)