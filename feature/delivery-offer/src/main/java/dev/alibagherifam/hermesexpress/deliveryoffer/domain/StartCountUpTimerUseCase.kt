package dev.alibagherifam.hermesexpress.deliveryoffer.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

class StartCountUpTimerUseCase {
    operator fun invoke(
        scope: CoroutineScope,
        totalTime: Duration,
        step: Duration,
        doAtTheEnd: suspend () -> Unit,
        doOnEachStep: suspend (Duration) -> Unit,
    ): Job = scope.launch {
        var elapsedTime = Duration.ZERO
        while (elapsedTime < totalTime) {
            delay(step)
            elapsedTime += step
            elapsedTime.coerceAtMost(totalTime)
            doOnEachStep(elapsedTime)
        }
        doAtTheEnd()
    }
}
