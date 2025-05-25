package com.ziko.data.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice3Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the words",
            "fire - far" to "lesson/lesson3/fire_far.mp3",
            "fire far"
        ),
        PracticeScreenContent(
            2,
            "Repeat the words",
            "our - or" to "lesson/lesson3/our_or.mp3",
            "our or"
        ),
        PracticeScreenContent(
            3,
            "Repeat the words",
            "player - play" to "lesson/lesson3/player_play.mp3",
            "player play"
        ),
        PracticeScreenContent(
            4,
            "Repeat the sentence",
            "The fire burned brightly in the dark night" to "lesson/lesson3/the_fire_burned_brightly_in_the_dark_place.mp3",
            "The fire burned brightly in the dark night"
        ),
        PracticeScreenContent(
            5,
            "Repeat the sentence",
            "Our house is near the tower" to "lesson/lesson3/our_house_is_near_the_tower.mp3",
            "Our house is near the tower"
        ),
        PracticeScreenContent(
            6,
            "Repeat the passage",
            "The lawyer spoke to the mayor about the power supply in their area. They sat near the fire and discussed their plans" to "lesson/lesson3/the_lawyer_spoke_to_the_mayor_about_the_power.mp3",
            "The lawyer spoke to the mayor about the power supply in their area. They sat near the fire and discussed their plans"
        ),
        PracticeScreenContent(
            7,
            "Repeat the poem",
            "The hour was late,\nthe fire was low,\nthe lawyer spoke in a voice so slow." to "lesson/lesson3/the_hour_was_late.mp3",
            "The hour was late, the fire was low, the lawyer spoke in a voice so slow."
        ),
    )
}
