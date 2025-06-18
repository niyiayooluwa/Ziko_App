package com.ziko.data.local.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice2Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the words",
            "say - sigh" to "lessons/lesson2/say_sigh.mp3",
            "say sigh"
        ),
        PracticeScreenContent(
            2,
            "Repeat the words",
            "boy - buy" to "lessons/lesson2/boy_buy.mp3",
            "boy buy"
        ),
        PracticeScreenContent(
            3,
            "Repeat the words",
            "show - cow" to "lessons/lesson2/show_cow.mp3",
            "show cow"
        ),
        PracticeScreenContent(
            4,
            "Repeat the sentence",
            "I may stay in the rain today" to "lessons/lesson2/i_may_stay_in_the_rain_today.mp3",
            "I may stay in the rain today"
        ),
        PracticeScreenContent(
            5,
            "Repeat the sentence",
            "The boy enjoys playing with toys" to "lessons/lesson2/the_boy_enjoys_playing_with_toys.mp3",
            "The boy enjoys playing with toys"
        ),
        PracticeScreenContent(
            6,
            "Repeat the sentence",
            "She found a rare chair near the window" to "lessons/lesson2/she_found_a_rare_chair_near.mp3",
            "She found a rare chair near the window"
        ),
        PracticeScreenContent(
            7,
            "Repeat the passage",
            "Kate wanted to buy a new chair for her home. She saw a boy playing near the store. The chair looked fair, but she was unsure about the price" to "lessons/lesson2/kate_wanted_to_buy_a_new_chair.mp3",
            "Kate wanted to buy a new chair for her home. She saw a boy playing near the store. The chair looked fair, but she was unsure about the price"
        ),
    )
}
