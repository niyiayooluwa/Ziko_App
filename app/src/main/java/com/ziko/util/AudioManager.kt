package com.ziko.util

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

object AudioManager {

    private var mediaPlayer: MediaPlayer? = null

    fun play(
        context: Context,
        @RawRes audioResId: Int,
        onStarted: () -> Unit = {},
        onFinished: () -> Unit = {}
    ) {
        stop()
        mediaPlayer = MediaPlayer.create(context, audioResId).apply {
            setOnCompletionListener {
                it.release()
                mediaPlayer = null
                onFinished()
            }
            onStarted()
            start()
        }
    }

    fun stop() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
    }
}
