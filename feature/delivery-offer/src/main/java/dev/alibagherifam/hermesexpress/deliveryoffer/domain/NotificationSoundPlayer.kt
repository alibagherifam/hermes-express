package dev.alibagherifam.hermesexpress.deliveryoffer.domain

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal suspend fun playNotificationSound(
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
