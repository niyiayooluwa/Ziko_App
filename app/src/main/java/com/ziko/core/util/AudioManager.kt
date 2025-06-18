package com.ziko.core.util

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ==================================================
 *  File: AudioManager.kt
 *  Layer: Utilities
 *
 *  Description:
 *  A singleton object responsible for managing playback of short
 *  audio clips (e.g., sound effects, voice lines) from the assets folder.
 *
 *  It uses [MediaPlayer] under the hood and provides lifecycle-aware
 *  behavior for pausing/stopping audio when the lifecycle owner is paused or destroyed.
 *
 *  The manager exposes the currently playing asset via a [StateFlow]
 *  to allow reactive observation (e.g., in Compose or ViewModel).
 *
 *  Usage:
 *  ```
 *  AudioManager.playAsset(context, "audio/welcome.mp3")
 *  ```
 *
 *  @see android.media.MediaPlayer
 * ==================================================
 */
object AudioManager : DefaultLifecycleObserver {

    // Underlying media player instance
    private var mediaPlayer: MediaPlayer? = null

    // Internal backing state to track the currently playing audio asset
    private val _currentlyPlaying = MutableStateFlow<String?>(null)

    /**
     * Public read-only state representing the current playing audio asset's path.
     * Observers can collect this flow to respond to playback changes.
     */
    val currentlyPlaying: StateFlow<String?> = _currentlyPlaying

    /**
     * Plays an audio asset from the assets directory using [MediaPlayer].
     *
     * Stops any currently playing sound before starting the new one.
     * Notifies via [onStarted] and [onFinished] callbacks for UI or logic hooks.
     *
     * @param context Android context used to access the asset file
     * @param assetPath Relative path to the audio file inside the assets folder
     * @param onStarted Callback invoked right before playback starts
     * @param onFinished Callback invoked when playback completes or fails
     *
     * @throws Exception if playback fails to initialize (caught internally)
     *
     * @see stop
     */
    fun playAsset(
        context: Context,
        assetPath: String,
        onStarted: () -> Unit = {},
        onFinished: () -> Unit = {}
    ) {
        try {
            stop() // Always stop any existing playback before starting new one
            Log.d("AudioManager", "Trying to play: $assetPath")

            val afd = context.assets.openFd(assetPath)

            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                setOnCompletionListener {
                    stop()
                    _currentlyPlaying.value = null
                    onFinished()
                }

                onStarted()
                _currentlyPlaying.value = assetPath
                start()
            }
        } catch (e: Exception) {
            // Fail silently but ensure callbacks and state are cleared
            e.printStackTrace()
            _currentlyPlaying.value = null
            onFinished()
        }
    }

    /**
     * Stops and releases the currently playing media, if any.
     *
     * Safe to call multiple times. Also resets [_currentlyPlaying] to null.
     */
    fun stop() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            reset()
            release()
        }
        mediaPlayer = null
        _currentlyPlaying.value = null
    }

    /**
     * Lifecycle hook that stops audio playback when the host is paused.
     *
     * @param owner Lifecycle owner invoking the pause
     */
    override fun onPause(owner: LifecycleOwner) = stop()

    /**
     * Lifecycle hook that stops audio playback when the host is destroyed.
     *
     * @param owner Lifecycle owner invoking the destruction
     */
    override fun onDestroy(owner: LifecycleOwner) = stop()
}
