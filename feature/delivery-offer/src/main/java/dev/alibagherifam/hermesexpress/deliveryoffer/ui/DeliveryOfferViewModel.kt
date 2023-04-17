package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.common.ui.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration

class DeliveryOfferViewModel(
    private val repository: DeliveryOfferRepository
) : BaseViewModel<DeliveryOfferUiState>(
    initialState = DeliveryOfferUiState()
) {
    private var acceptOfferJob: Job? = null
    private var expireOfferJob: Job? = null

    init {
        repository.getOfferFlow().onEach { offer ->
            _uiState.update {
                it.copy(
                    offer = offer,
                    offerTimeElapsed = Duration.ZERO
                )
            }
            stopExpireOfferJob()
            if (offer != null) {
                startExpireOfferJob(offer)
            }
        }.launchIn(safeScope)
    }

    private fun startExpireOfferJob(offer: DeliveryOffer) {
        expireOfferJob = startCountUpTimer(
            totalTime = offer.timeToLive,
            step = offerExpirationProgressStep,
            doOnEachStep = { elapsedTime ->
                _uiState.update { it.copy(offerTimeElapsed = elapsedTime) }
                while (isUserAcceptingOffer()) {
                    // Pause expiration during offer acceptance
                    delay(offerExpirationProgressStep)
                }
            },
            doAtTheEnd = { repository.removeOffer() }
        )
    }

    private fun stopExpireOfferJob() {
        expireOfferJob?.cancel()
        expireOfferJob = null
    }

    fun onAcceptOfferPressStateChange(isPressed: Boolean) {
        println("is Press = $isPressed")
        acceptOfferJob = if (isPressed) {
            startCountUpTimer(
                totalTime = timeToConfirmOffer,
                step = with(Duration) { 1.seconds },
                doAtTheEnd = { acceptOffer() }
            )
        } else {
            acceptOfferJob?.cancel()
            null
        }
    }

    private fun isUserAcceptingOffer() = (acceptOfferJob != null)

    private fun startCountUpTimer(
        totalTime: Duration,
        step: Duration,
        doAtTheEnd: suspend () -> Unit,
        doOnEachStep: suspend (Duration) -> Unit = {},
    ): Job = safeScope.launch {
        var elapsedTime = Duration.ZERO
        while (elapsedTime < totalTime) {
            delay(step)
            elapsedTime += step
            elapsedTime.coerceAtMost(totalTime)
            doOnEachStep(elapsedTime)
        }
        doAtTheEnd()
    }

    private fun acceptOffer() = safeLaunch {
        stopExpireOfferJob()
        _uiState.update {
            it.copy(isAcceptingOfferInProgress = true)
        }
        repository.acceptOffer()
        _uiState.update {
            it.copy(
                isAcceptingOfferInProgress = false,
                isOfferAccepted = true
            )
        }
    }

    fun resetState() {
        _uiState.update { DeliveryOfferUiState() }
    }

    override fun handleIOException(exception: Throwable) {
        TODO("Not yet implemented")
    }

    private companion object {
        val timeToConfirmOffer = with(Duration) { 3.seconds }
        val offerExpirationProgressStep = with(Duration) { 50.milliseconds }
    }
}
