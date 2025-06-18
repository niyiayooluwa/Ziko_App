package com.ziko.core.util

/**
 * Returns the ID of the next lesson by incrementing the numeric suffix of the current ID.
 *
 * This function assumes that the lesson ID is in the format of a prefix (non-digit characters)
 * followed by a numeric suffix (e.g., "lesson1", "module05"). It extracts the numeric part,
 * increments it by 1, and returns the new ID with the same prefix.
 *
 * If the numeric part is not found or cannot be parsed, the original ID is returned unchanged.
 *
 * This is useful for navigating to the next lesson in a sequence or generating consecutive IDs.
 *
 * @param currentId The current lesson ID (e.g., "lesson1", "module05")
 * @return The next lesson ID with incremented numeric value (e.g., "lesson2", "module06")
 *
 * @sample getNextLessonId("lesson1") // returns "lesson2"
 * @sample getNextLessonId("module09") // returns "module10"
 */
fun getNextLessonId(currentId: String): String {
    val prefix = currentId.takeWhile { !it.isDigit() }
    val number = currentId.dropWhile { !it.isDigit() }.toIntOrNull() ?: return currentId
    return "$prefix${number + 1}"
}


/**
 * Normalizes a given text string by converting it to lowercase, removing all non-alphanumeric characters
 * (except spaces), and trimming leading/trailing whitespace.
 *
 * This function is typically used to prepare strings for comparison, searching, or indexing by ensuring
 * a consistent and simplified format.
 *
 * @param text The input string to be normalized (e.g., " Hello, World! ")
 * @return A normalized string with only lowercase letters, digits, and spaces (e.g., "hello world")
 *
 * @sample normalizeText(" Hello, World! ") // returns "hello world"
 * @sample normalizeText("DATA@2025")       // returns "data2025"
 *
 * @see java.lang.String.lowercase
 * @see java.lang.String.trim
 */
fun normalizeText(text: String): String {
    return text
        .lowercase()
        .replace(Regex("[^a-zA-Z0-9 ]"), "")
        .trim()
}
