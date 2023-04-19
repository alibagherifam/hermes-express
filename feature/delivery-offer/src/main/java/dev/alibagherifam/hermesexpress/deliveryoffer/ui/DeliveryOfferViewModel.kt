package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.common.ui.BaseViewModel
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import dev.alibagherifam.hermesexpress.common.R as CommonR

internal class DeliveryOfferViewModel(
    private val repository: DeliveryOfferRepository,
    private val stringProvider: StringProvider
) : BaseViewModel<DeliveryOfferUiState>(
    initialState = DeliveryOfferUiState()
) {
    private var offerAcceptanceConfirmationJob: Job? = null
    private var offerExpirationJob: Job? = null

    init {
        repository.getOfferFlow().onEach { offer ->
            _uiState.update {
                it.copy(offer = offer)
            }
            cancelOfferExpiration()
            if (offer != null) {
                startOfferExpiration(offer.timeToLive)
            }
        }.launchIn(safeScope)
    }

    fun onNewEvent(event: DeliveryOfferEvent) {
        when (event) {
            is DeliveryOfferEvent.AcceptOfferPressStateChange -> {
                if (event.isPressed) {
                    startOfferAcceptanceConfirmation()
                } else {
                    cancelOfferAcceptanceConfirmation()
                }
            }

            is DeliveryOfferEvent.UserMessageShown -> {
                consumeUserMessage(event.message)
            }
        }
    }

    private fun startOfferExpiration(expirationDuration: Duration) {
        offerExpirationJob = startCountUpTimer(
            totalTime = expirationDuration,
            step = smoothTimerStep,
            doOnEachStep = { elapsedTime ->
                _uiState.update { it.copy(offerTimeElapsed = elapsedTime) }
                while (isAcceptingOfferJobRunning()) {
                    // Pause expiration during offer acceptance
                    delay(smoothTimerStep)
                }
            },
            doAtTheEnd = {
                repository.removeOffer()
                _uiState.update { it.copy(isOfferExpired = true) }
            }
        )
    }

    private fun cancelOfferExpiration() {
        offerExpirationJob?.cancel()
        offerExpirationJob = null
    }

    private fun startOfferAcceptanceConfirmation() {
        offerAcceptanceConfirmationJob = startCountUpTimer(
            totalTime = timeToConfirmOfferAcceptance,
            step = smoothTimerStep,
            doAtTheEnd = { acceptOffer() },
            doOnEachStep = { elapsedTime ->
                val percentage = (elapsedTime / timeToConfirmOfferAcceptance).toFloat()
                _uiState.update {
                    it.copy(offerAcceptanceConfirmationPercentage = percentage)
                }
            }
        )
    }

    private fun cancelOfferAcceptanceConfirmation() {
        _uiState.update {
            it.copy(offerAcceptanceConfirmationPercentage = 0f)
        }
        offerAcceptanceConfirmationJob?.cancel()
        offerAcceptanceConfirmationJob = null
    }

    private fun isAcceptingOfferJobRunning() = (offerAcceptanceConfirmationJob != null)

    private fun startCountUpTimer(
        totalTime: Duration,
        step: Duration,
        doAtTheEnd: suspend () -> Unit,
        doOnEachStep: suspend (Duration) -> Unit,
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
        cancelOfferExpiration()
        _uiState.update {
            it.copy(isAcceptingOfferInProgress = true)
        }
        repository.acceptOffer()
        val message = stringProvider.getString(R.string.message_offer_accepted)
        _uiState.update {
            it.copy(
                isAcceptingOfferInProgress = false,
                isOfferAccepted = true,
                userMessages = it.userMessages + message
            )
        }
    }

    override fun handleIOException(exception: Throwable) {
        val errorMessage = stringProvider.getString(CommonR.string.message_generic_io_error)
        _uiState.update {
            it.copy(
                userMessages = it.userMessages + errorMessage
            )
        }
    }

    private fun consumeUserMessage(message: String) {
        _uiState.update { oldState ->
            oldState.copy(
                userMessages = oldState.userMessages.filterNot { it == message }
            )
        }
    }

    private companion object {
        val timeToConfirmOfferAcceptance = with(Duration) { 2.seconds }
        val smoothTimerStep = with(Duration) { 50.milliseconds }
    }
}
