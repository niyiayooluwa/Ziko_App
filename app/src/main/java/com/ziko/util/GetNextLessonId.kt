package com.ziko.util

fun getNextLessonId(currentId: String): String {
    val prefix = currentId.takeWhile { !it.isDigit() }
    val number = currentId.dropWhile { !it.isDigit()}.toIntOrNull() ?: return currentId
    return "$prefix${number + 1}"
}