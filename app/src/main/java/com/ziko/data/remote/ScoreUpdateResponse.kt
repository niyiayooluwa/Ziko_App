package com.ziko.data.remote

data class ScoreUpdateResponse(
    val msg: String?,
    val errorMsg: String?,
    val data: ScoreUpdateData?
)

data class ScoreUpdateData(
    val accuracy: Int,
    val highest_score: Int
)

