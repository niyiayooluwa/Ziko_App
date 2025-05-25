package com.ziko.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.*

class SpeechManager(
    context: Context,
    private val onRmsChangedCallback: (Float) -> Unit,  // Renamed to avoid conflict
    private val onResultCallback: (String?) -> Unit,    // Renamed for consistency
    private val onListeningStateChangedCallback: (Boolean) -> Unit  // Renamed for consistency
) {
    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }

    init {
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                onListeningStateChangedCallback(true)
            }

            override fun onBeginningOfSpeech() {
                onListeningStateChangedCallback(true)
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Now calls the callback parameter, not itself!
                onRmsChangedCallback(rmsdB)
            }

            override fun onEndOfSpeech() {
                onListeningStateChangedCallback(false)
            }

            override fun onError(error: Int) {
                onListeningStateChangedCallback(false)
                onResultCallback(null)
            }

            override fun onResults(results: Bundle?) {
                onListeningStateChangedCallback(false)
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                onResultCallback(matches?.firstOrNull())
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
        })
    }

    fun startListening() {
        recognizer.startListening(recognizerIntent)
    }

    fun stopListening() {
        recognizer.stopListening()
    }

    fun destroy() {
        recognizer.destroy()
    }
}