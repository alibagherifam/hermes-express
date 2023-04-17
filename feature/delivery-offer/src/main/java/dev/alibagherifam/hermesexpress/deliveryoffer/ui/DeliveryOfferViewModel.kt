package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import dev.alibagherifam.hermesexpress.common.R
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.common.ui.BaseViewModel
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration

internal class DeliveryOfferViewModel(
    private val repository: DeliveryOfferRepository,
    private val stringProvider: StringProvider
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
            cancelExpireOfferJob()
            if (offer != null) {
                startExpireOfferJob(offer)
            }
        }.launchIn(safeScope)
    }

    fun onNewEvent(event: DeliveryOfferEvent) {
        when (event) {
            is DeliveryOfferEvent.AcceptOfferPressStateChange -> {
                if (event.isPressed) {
                    startAcceptingOfferJob()
                } else {
                    cancelAcceptingOfferJob()
                }
            }

            is DeliveryOfferEvent.UserMessageShown -> {
                consumeUserMessage(event.message)
            }
        }
    }

    private fun startExpireOfferJob(offer: DeliveryOffer) {
        expireOfferJob = startCountUpTimer(
            totalTime = offer.timeToLive,
            step = smoothTimerStep,
            doOnEachStep = { elapsedTime ->
                _uiState.update { it.copy(offerTimeElapsed = elapsedTime) }
                while (isAcceptingOfferJobRunning()) {
                    // Pause expiration during offer acceptance
                    delay(smoothTimerStep)
                }
            },
            doAtTheEnd = { repository.removeOffer() }
        )
    }

    private fun cancelExpireOfferJob() {
        expireOfferJob?.cancel()
        expireOfferJob = null
    }

    private fun startAcceptingOfferJob() {
        acceptOfferJob = startCountUpTimer(
            totalTime = timeToConfirmOffer,
            step = smoothTimerStep,
            doAtTheEnd = { acceptOffer() },
            doOnEachStep = { elapsedTime ->
                val percentage = (elapsedTime / timeToConfirmOffer).toFloat()
                _uiState.update {
                    it.copy(offerAcceptancePercentage = percentage)
                }
            }
        )
    }

    private fun cancelAcceptingOfferJob() {
        _uiState.update {
            it.copy(offerAcceptancePercentage = 0f)
        }
        acceptOfferJob?.cancel()
        acceptOfferJob = null
    }

    private fun isAcceptingOfferJobRunning() = (acceptOfferJob != null)

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
        cancelExpireOfferJob()
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
        val errorMessage = stringProvider.getString(R.string.message_generic_io_error)
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
        val timeToConfirmOffer = with(Duration) { 3.seconds }
        val smoothTimerStep = with(Duration) { 50.milliseconds }
    }
}
