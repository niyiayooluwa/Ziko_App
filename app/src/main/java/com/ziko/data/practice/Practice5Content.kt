package com.ziko.data.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice5Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the words",
            "bat - pat" to "lesson/lesson5/bat_pat.mp3",
            "bat pat"
        ),
        PracticeScreenContent(
            2,
            "Repeat the words",
            "bag - back" to "lesson/lesson5/bag_back.mp3",
            "bag back"
        ),
        PracticeScreenContent(
            3,
            "Repeat the words",
            "zip - sip" to "lesson/lesson5/zip_sip.mp3",
            "zip sip"
        ),
        PracticeScreenContent(
            4,
            "Repeat the sentence",
            "Peter picked fresh peaches from the farm" to "lesson/lesson5/peter_picked_fresh.mp3",
            "Peter picked fresh peaches from the farm"
        ),
        PracticeScreenContent(
            5,
            "Repeat the sentence",
            "She washed the dishes in the sink" to "lesson/lesson5/she_washed_the_dishes.mp3",
            "She washed the dishes in the sink"
        ),
        PracticeScreenContent(
            6,
            "Repeat the sentence",
            "Charlie chews chocolate carefully." to "lesson/lesson5/charlie_chews_chocolate.mp3",
            "Charlie chews chocolate carefully"
        ),
        PracticeScreenContent(
            7,
            "Repeat the passage",
            "Tommy took a trip to a shop. He bought fish and cheese. The teacher told him to check the list twice." to "lesson/lesson5/tommy_took_a_trip_to_the_shop.mp3",
            "Tommy took a trip to a shop. He bought fish and cheese. The teacher told him to check the list twice."
        ),
    )
}
