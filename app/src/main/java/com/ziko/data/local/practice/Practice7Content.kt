package com.ziko.data.local.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice7Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the words",
            "TAble" to "lessons/lesson7/table.mp3",
            "table"
        ),
        PracticeScreenContent(
            2,
            "Repeat the words",
            "WINdow" to "lessons/lesson7/window.mp3",
            "window"
        ),
        PracticeScreenContent(
            3,
            "Repeat the words",
            "reMEMber" to "lessons/lesson7/remember.mp3",
            "remember"
        ),
        PracticeScreenContent(
            4,
            "Repeat the words",
            "aFRAID" to "lessons/lesson7/afraid.mp3",
            "afraid"
        ),
        PracticeScreenContent(
            5,
            "Repeat the sentence with the correct sentence stress",
            "She wants to BUY a NEW house" to "lessons/lesson7/she_wants_to_buy.mp3",
            "she wants to buy a new house"
        ),
        PracticeScreenContent(
            6,
            "Repeat the poem with the correct sentence stress",
            "the SUN is high,\nthe SKY is blue,\nthe BIRDS are singing,\njust for YOU" to "lessons/lesson7/the_sun_is_high.mp3",
            "the sun is high, the sky is blue, the birds are singing, just for you"
        ),
    )
}
