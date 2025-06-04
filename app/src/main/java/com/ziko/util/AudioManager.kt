

/*object AudioManager : DefaultLifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null

    fun playAsset(
        context: Context,
        assetPath: String,
        onStarted: () -> Unit = {},
        onFinished: () -> Unit = {}
    ) {
        try {
            stop() // kill any currently playing audio

            val afd = context.assets.openFd(assetPath)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                setOnCompletionListener {
                    stop() // safe clean-up
                    onFinished()
                }
                onStarted()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onFinished()
        }
    }

    fun stop() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            reset()
            release()
        }
        mediaPlayer = null
    }

    override fun onPause(owner: LifecycleOwner) {
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        stop()
    }
}*/
package com.ziko.util

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AudioManager : DefaultLifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null

    private val _currentlyPlaying = MutableStateFlow<String?>(null)
    val currentlyPlaying: StateFlow<String?> = _currentlyPlaying

    fun playAsset(
        context: Context,
        assetPath: String,
        onStarted: () -> Unit = {},
        onFinished: () -> Unit = {}
    ) {
        try {
            stop()

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
            e.printStackTrace()
            _currentlyPlaying.value = null
            onFinished()
        }
    }

    fun stop() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            reset()
            release()
        }
        mediaPlayer = null
        _currentlyPlaying.value = null
    }

    override fun onPause(owner: LifecycleOwner) = stop()
    override fun onDestroy(owner: LifecycleOwner) = stop()
}


