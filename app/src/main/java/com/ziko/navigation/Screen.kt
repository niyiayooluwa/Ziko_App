package com.ziko.navigation

sealed class Screen(val route: String) {
    // Core App Flow
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
    data object SignOne : Screen("signup_one")  // Child
    data object SignTwo : Screen("signup_two")  // Child
    data object Home : Screen("Home")
    data object Assessment : Screen("assessment")

    //Lesson Flow
    data class LessonLoading(val lessonId: String) : Screen("lesson_loading/$lessonId") {
        companion object{ const val BASE_ROUTE = "lesson_loading/{lessonId}"}
    }
    data class LessonIntro(val lessonId: String) : Screen("lesson_intro/$lessonId") {
        companion object { const val BASE_ROUTE = "lesson_intro/{lessonId}" }
    }
    data class LessonContent(val lessonId: String) : Screen("lesson_content/$lessonId") {
        companion object { const val BASE_ROUTE = "lesson_content/{lessonId}" }
    }
    data class LessonCompletion(val lessonId: String): Screen("lesson_completion/$lessonId") {
        companion object { const val BASE_ROUTE = "lesson_completion/{lessonId}" }
    }

    //Practice Flow
    data class PracticeLoading(val lessonId: String) : Screen("practice_loading/$lessonId") {
        companion object{ const val BASE_ROUTE = "practice_loading/{lessonId}"}
    }
    data class PracticeContent(val lessonId: String): Screen("practice_content/$lessonId") {
        companion object{const val BASE_ROUTE = "practice_content/{lessonId}"}
    }
    data class PracticeCompletion(val lessonId: String): Screen("practice_completion/$lessonId") {
        companion object{const val BASE_ROUTE = "practice_completion/{lessonId}"}
    }

    //Assessment Flow
    data class AssessmentLoading(val lessonId: String) : Screen("assessment_loading/$lessonId") {
        companion object{ const val BASE_ROUTE = "assessment_loading/{lessonId}"}
    }
    data class AssessmentContent(val lessonId: String): Screen("assessment_content/$lessonId") {
        companion object{const val BASE_ROUTE = "assessment_content/{lessonId}"}
    }
    data class AssessmentCompletion(val lessonId: String): Screen("assessment_completion/$lessonId") {
        companion object{const val BASE_ROUTE = "assessment_completion/{lessonId}"}
    }
}