package com.ziko.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ziko.data.model.AudioManifest

fun loadAudioManifest(context: Context): AudioManifest {
    val jsonString = context.assets.open("audio_manifest.json")
        .bufferedReader()
        .use { it.readText() }

    val gson = Gson()
    val type = object : TypeToken<AudioManifest>() {}.type
    return gson.fromJson(jsonString, type)
}
