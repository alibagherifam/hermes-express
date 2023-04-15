package dev.alibagherifam.hermesexpress.deliveryoffer.domain

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun playNotificationSound(
    context: Context,
    @RawRes soundResId: Int
) = suspendCancellableCoroutine { continuation ->
    val mediaPlayer = MediaPlayer.create(context, soundResId)
    mediaPlayer.setOnCompletionListener {
        mediaPlayer.release()
        continuation.resume(Unit)
    }
    mediaPlayer.start()
    continuation.invokeOnCancellation {
        mediaPlayer.release()
    }
}

@Deprecated(message = "Use playNotificationSound() instead")
class NotificationSoundPlayer(@RawRes private val mediaResId: Int) {
    private var mediaPlayer: MediaPlayer? = null

    fun prepare(context: Context) {
        mediaPlayer = MediaPlayer.create(context, mediaResId)
    }

    fun play() {
        val player = checkNotNull(mediaPlayer) { "Call prepare() before play()" }
        player.run {
            if (isPlaying) {
                pause()
            }
            seekTo(0)
            start()
        }
    }

    fun release() {
        mediaPlayer?.run {
            stop()
            release()
        }
    }
}
