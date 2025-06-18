package com.ziko.navigation

/**
 * Represents all possible navigation destinations within the application.
 *
 * Each screen is either a singleton [data object] for static routes or a [data class]
 * for dynamic routes with parameters.
 *
 * The [route] string defines the navigation path used by the NavController.
 */
sealed class Screen(val route: String) {

    // region --- Core App Flow ---

    /** Splash screen shown on app launch */
    data object Splash : Screen("splash")

    /** Onboarding screen shown on first launch */
    data object Onboarding : Screen("onboarding")

    /** Login screen for user authentication */
    data object Login : Screen("login")

    /** First step of signup process */
    data object SignOne : Screen("signup_one")

    /** Second step of signup process */
    data object SignTwo : Screen("signup_two")

    /** Home screen after successful login/signup */
    data object Home : Screen("Home")

    /** General assessment overview screen */
    data object Assessment : Screen("assessment")

    // endregion


    // region --- Lesson Flow ---

    /**
     * Pre-lesson loading screen for preparing resources.
     * @param lessonId Unique identifier for the lesson.
     */
    data class PreLessonLoading(val lessonId: String) : Screen("prelesson_loading/$lessonId") {
        companion object {
            const val BASE_ROUTE = "prelesson_loading/{lessonId}"
        }
    }

    /** Lesson loading screen (actual lesson assets). */
    data class LessonLoading(val lessonId: String) : Screen("lesson_loading/$lessonId") {
        companion object {
            const val BASE_ROUTE = "lesson_loading/{lessonId}"
        }
    }

    /** Lesson introduction screen. */
    data class LessonIntro(val lessonId: String) : Screen("lesson_intro/$lessonId") {
        companion object {
            const val BASE_ROUTE = "lesson_intro/{lessonId}"
        }
    }

    /** Main lesson content screen. */
    data class LessonContent(val lessonId: String) : Screen("lesson_content/$lessonId") {
        companion object {
            const val BASE_ROUTE = "lesson_content/{lessonId}"
        }
    }

    /** Final screen after completing a lesson. */
    data class LessonCompletion(val lessonId: String) : Screen("lesson_completion/$lessonId") {
        companion object {
            const val BASE_ROUTE = "lesson_completion/{lessonId}"
        }
    }

    // endregion


    // region --- Practice Flow ---

    /** Prepares practice session resources. */
    data class PracticeLoading(val lessonId: String) : Screen("practice_loading/$lessonId") {
        companion object {
            const val BASE_ROUTE = "practice_loading/{lessonId}"
        }
    }

    /** Main practice content screen. */
    data class PracticeContent(val lessonId: String) : Screen("practice_content/$lessonId") {
        companion object {
            const val BASE_ROUTE = "practice_content/{lessonId}"
        }
    }

    /** Final screen shown after finishing a practice session. */
    data class PracticeCompletion(val lessonId: String) : Screen("practice_completion/$lessonId") {
        companion object {
            const val BASE_ROUTE = "practice_completion/{lessonId}"
        }
    }

    // endregion


    // region --- Assessment Flow ---
    /** Assessment loading screen. */
    data class AssessmentLoading(val lessonId: String) : Screen("assessment_loading/$lessonId") {
        companion object {
            const val BASE_ROUTE = "assessment_loading/{lessonId}"
        }
    }

    /** Main assessment content screen. */
    data class AssessmentContent(val lessonId: String) : Screen("assessment_content/$lessonId") {
        companion object {
            const val BASE_ROUTE = "assessment_content/{lessonId}"
        }
    }

    /**
     * Final screen after completing the assessment.
     *
     * @param lessonId ID of the completed lesson.
     * @param score User's final score.
     * @param correctAnswers Number of correct answers.
     * @param totalQuestions Total number of questions.
     * @param timeSpent Time taken to complete the assessment.
     */
    data class AssessmentCompletion(
        val lessonId: String,
        val score: Int,
        val correctAnswers: Int,
        val totalQuestions: Int,
        val timeSpent: String
    ) : Screen(
        "assessment_completion/$lessonId/$score/$correctAnswers/$totalQuestions/$timeSpent"
    ) {
        companion object {
            const val BASE_ROUTE = "assessment_completion/{lessonId}/{score}/{correctAnswers}/{totalQuestions}/{timeSpent}"
        }
    }

    // endregion


    // region --- Profile & Settings Flow ---

    /** User profile screen. */
    data object Profile : Screen("profile")

    /** Screen to manage user security and credentials. */
    data object SecurityScreen : Screen("security")

    // endregion
}
