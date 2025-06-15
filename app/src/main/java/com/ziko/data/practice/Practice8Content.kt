package com.ziko.data.practice

import com.ziko.ui.model.PracticeScreenContent

fun getPractice8Content(): List<PracticeScreenContent> {
    return listOf(
        PracticeScreenContent(
            id =1,
            instructions = "Repeat the poem with the correct rhythm pattern",
            alignment = true,
            sound = "I walk through the woods in the early light\nThe birds start to sing, and the sky shines bright.\nThe river flows fast, with a soft, sweet sound\nIts rhythm so smooth, as it moves around\nThe leaves dance high in the morning breeze\nThey rustle and whisper among the trees\nThe rhythm of nature is clear and true\nA song of the earth in a golden hue"
                        to "lessons/lesson8/i_walk_through_the_woods.mp3",
            expectedPhrase = "I walk through the woods in the early light. The birds start to sing, and the sky shines bright. The river flows fast, with a soft, sweet sound. Its rhythm so smooth, as it moves around. The leaves dance high in the morning breeze. They rustle and whisper among the trees.The rhythm of nature is clear and true.A song of the earth in a golden hue"
        )
    )
}
