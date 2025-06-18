package com.ziko.data.model

import com.ziko.ui.model.LessonIntroContent

object LessonIntroContentProvider {
    fun getIntroContent(lessonId: String): LessonIntroContent =
        when (lessonId) {
            "lesson1" -> LessonIntroContent(
                definitionTextOne = "Monophthongs",
                definitionTextTwo = " are single pure vowel sounds that do not change in quality during articulation.",
                definitionAudio = "lessons/lesson1/monophthong_definition.mp3",
                points = listOf(
                    "Types of Monophthongs",
                    "  • Short vowels",
                    "    /ɪ/, /e/, /æ/, /ʌ/, /ɒ/, /ʊ/, /ə/",
                    "  • Long vowels",
                    "    /iː/, /ɑː/, /ɔː/, /uː/, /ɜː/"
                )
            )

            "lesson2" -> LessonIntroContent(
                definitionTextOne = "Diphthongs",
                definitionTextTwo = " are vowel sounds, that glide from one position to another within a syllable.",
                definitionAudio = "lessons/lesson2/diphthong_meaning.mp3",
                points = listOf(
                    "Key Characteristics",
                    "•\tA smooth glide between two vowel sounds.",
                    "•\tThey are considered single phonemes, not two separate sounds.",
                    "•\tThe first part is typically more prominent, while the second part is weaker.",
                    "",
                    "Types of Diphthongs:",
                    "•\t/eɪ/, /aɪ/, /ɔɪ/, /aʊ/, /əʊ/, /ɪə/, /eə/, /ʊə/"
                )
            )

            "lesson3" -> LessonIntroContent(
                definitionTextOne = "Triphthongs",
                definitionTextTwo = "are combination of three vowels sounds pronounced in one syllable.",
                definitionAudio = "lessons/lesson3/triphthongs_definition.mp3",
                points = listOf(
                    "Types of Triphthongs:",
                    "•\t/aɪə/, /aʊə/, /ɔɪə/, /eɪə/, /əʊə/"
                )
            )

            "lesson4" -> LessonIntroContent(
                definitionTextOne = "Voiced Consonants",
                definitionTextTwo = " are produced when the vocal cords vibrate.",
                definitionAudio = "lessons/lesson4/voiced_consonant_definition.mp3",
                points = listOf(
                    "Types of voiced consonants",
                    "•\t/b/, /d/, /g/, /v/, /ð/, /z/, /ʒ/,/dʒ/"
                )
            )

            "lesson5" -> LessonIntroContent(
                definitionTextOne = "Voiceless Consonants",
                definitionTextTwo = " are produced without vocal cord vibration.",
                definitionAudio = "lessons/lesson5/voiceless_consonants_definition.mp3",
                points = listOf(
                    "Types of voiceless consonants",
                    "•\t/p/, /t/, /k/, /f/, /θ/, /s/, /ʃ/, /tʃ/"
                )
            )

            "lesson6" -> LessonIntroContent(
                definitionTextOne = "Intonation",
                definitionTextTwo = " is the rise and fall of pitch in a speech.",
                definitionAudio = "lessons/lesson6/intonation_definition.mp3",
                points = listOf(
                    "Types of intonation:",
                    "•\tRising intonation - Used in questions.",
                    "•\tFalling intonation - used in statements",
                    "•\tMixed intonation - Used in uncertainty"
                )
            )

            "lesson7" -> LessonIntroContent(
                definitionTextOne = "Stress",
                definitionTextTwo = " is the emphasis placed on a particular syllable in a word or a particular word in a sentence." +
                        "It affects pronunciation, rhythm, and meaning",
                definitionAudio = "lessons/lesson7/stress_definition.mp3",
            )

            "lesson8" -> LessonIntroContent(
            definitionTextOne = "",
            definitionTextTwo = " English is a stress-timed language, meaning syllables occur at regular intervals, while unstressed syllables are shortened.",
            definitionAudio = "lessons/lesson8/rhythm_definition.mp3",
        )

            else -> LessonIntroContent(
                definitionTextOne = "Monophthongs",
                definitionTextTwo = " are single pure vowel sounds that do not change in quality during articulation.",
                definitionAudio = "lessons/lesson1/.mp3",
                points = listOf(
                    "Types of Monophthongs",
                    "  • Short vowels",
                    "    /ɪ/, /e/, /æ/, /ʌ/, /ɒ/, /ʊ/, /ə/",
                    "  • Long vowels",
                    "    /iː/, /ɑː/, /ɔː/, /uː/, /ɜː/"
                )
            )
        }
}