package com.ziko.data.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice8Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            1,
            "Repeat the poem with the correct rhythm pattern",
            sound8 = listOf(
                "I walk through the woods in the early light" to "lessons/lesson8/i_walk_through.mp3",
                "The birds start to sing, and the sky shines bright" to "lessons/lesson1/the_birds_start_to_sing.mp3",
                "The river flows fast, with a soft, sweet sound" to "lessons/lesson1/the_river_flows_fast.mp3",
                "Its rhythm so smooth, as it moves around" to "lessons/lesson1/its_rhythm_so_smooth.mp3",
                "The leaves dance high in the morning breeze" to "lessons/lesson1/the_leaves_dance_high.mp3",
                "They rustle and whisper among the trees" to "lessons/lesson1/the_rustle_and_whisper.mp3",
                "The rhythm of nature is clear and true" to "lessons/lesson1/the_rhythm_of_nature.mp3",
                "A song of the earth in a golden hue" to "lessons/lesson1/the_song_of_the.mp3",
            ),
            expectedPhrase = "I walk through the woods in the early light. The birds start to sing, and the sky shines bright. The river flows fast, with a soft, sweet sound. Its rhythm so smooth, as it moves around. The leaves dance high in the morning breeze. They rustle and whisper among the trees.The rhythm of nature is clear and true.A song of the earth in a golden hue"
        )
    )
}
