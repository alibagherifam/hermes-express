package dev.alibagherifam.hermesexpress.deliveryoffer

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RawRes
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.time.Duration

internal suspend fun playAudio(
    context: Context,
    @RawRes audioResId: Int
) = suspendCancellableCoroutine { continuation ->
    val mediaPlayer = MediaPlayer.create(context, audioResId)
    mediaPlayer.setOnCompletionListener {
        mediaPlayer.release()
        continuation.resume(Unit)
    }
    mediaPlayer.start()
    continuation.invokeOnCancellation {
        mediaPlayer.release()
    }
}

internal fun vibrateDevice(
    context: Context,
    duration: Duration
) {
    val vibrator = if (Build.VERSION.SDK_INT >= 31) {
        val vibratorService = Context.VIBRATOR_MANAGER_SERVICE
        val vibratorManager = context.getSystemService(vibratorService) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                duration.inWholeMilliseconds,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        vibrator.vibrate(duration.inWholeMilliseconds)
    }
}
