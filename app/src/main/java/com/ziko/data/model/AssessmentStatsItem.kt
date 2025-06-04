package com.ziko.data.model

import com.google.gson.annotations.SerializedName

data class AssessmentStatsItem(
    @SerializedName("lesson")
    val title: String,

    @SerializedName("highest_score")
    val highestScore: Int?,

    @SerializedName("accuracy")
    val accuracy: Int?
)

