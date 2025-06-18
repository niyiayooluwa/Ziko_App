package com.ziko.core.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.*

/**
 * A helper class that wraps Android's [SpeechRecognizer] to provide voice recognition functionality.
 *
 * This class simplifies voice input by abstracting away the boilerplate of setting up and managing
 * [SpeechRecognizer] and its [RecognitionListener].
 *
 * It provides callbacks to track:
 * - RMS volume changes (for visual feedback like waveform)
 * - Recognition result (full sentence or phrase)
 * - Listening state (start/end/error)
 *
 * @param context The application or activity context.
 * @param onRmsChangedCallback A function called with the current RMS dB value for visual feedback.
 * @param onResultCallback A function called with the final recognized speech result (or null on error).
 * @param onListeningStateChangedCallback A function called with a boolean indicating if the recognizer is active.
 *
 * @see android.speech.SpeechRecognizer
 * @see android.speech.RecognizerIntent
 */
class SpeechManager(
    context: Context,
    private val onRmsChangedCallback: (Float) -> Unit,
    private val onResultCallback: (String?) -> Unit,
    private val onListeningStateChangedCallback: (Boolean) -> Unit
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

    /**
     * Starts listening for voice input using the initialized recognizer intent.
     */
    fun startListening() {
        recognizer.startListening(recognizerIntent)
    }

    /**
     * Stops ongoing voice recognition session manually.
     */
    fun stopListening() {
        recognizer.stopListening()
    }

    /**
     * Releases resources associated with the speech recognizer.
     */
    fun destroy() {
        recognizer.destroy()
    }
}
