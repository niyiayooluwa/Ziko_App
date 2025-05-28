package com.ziko.util

fun normalizeText (text: String): String {
    return text
        .lowercase()
        .replace(Regex("[^a-zA-Z0-9 ]"), "")
        .trim()
}