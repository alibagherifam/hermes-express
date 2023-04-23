package dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen

import androidx.lifecycle.viewModelScope
import dev.alibagherifam.hermesexpress.common.ui.BaseViewModel
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.FormatCurrencyUseCase
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.StartCountUpTimerUseCase
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import dev.alibagherifam.hermesexpress.common.R as CommonR

internal class DeliveryOfferViewModel(
    private val formatCurrencyUseCase: FormatCurrencyUseCase,
    private val repository: DeliveryOfferRepository,
    private val startCountUpTimer: StartCountUpTimerUseCase,
    private val stringProvider: StringProvider
) : BaseViewModel<DeliveryOfferUiState>(
    initialState = DeliveryOfferUiState()
) {
    private var offerAcceptanceConfirmationJob: Job? = null
    private var offerExpirationJob: Job? = null

    init {
        viewModelScope.launch {
            val offer = checkNotNull(repository.receivedOffer.first())
            _uiState.update {
                it.copy(offer = offer.toUiModel(formatCurrencyUseCase))
            }
            startOfferExpiration(offer.timeToLive)
        }
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
            scope = safeScope,
            totalTime = expirationDuration,
            step = smoothTimerStep,
            doOnEachStep = { elapsedTime ->
                _uiState.update { it.copy(offerTimeElapsed = elapsedTime) }
                while (offerAcceptanceConfirmationJob?.isActive == true) {
                    // Pause expiration during offer acceptance
                    delay(smoothTimerStep)
                }
            },
            doAtTheEnd = {
                repository.ignoreOffer()
                _uiState.update { it.copy(isOfferExpired = true) }
            }
        )
    }

    private fun cancelOfferExpiration() {
        offerExpirationJob?.cancel()
        offerExpirationJob = null
        _uiState.update {
            it.copy(offerTimeElapsed = Duration.ZERO)
        }
    }

    private fun startOfferAcceptanceConfirmation() {
        offerAcceptanceConfirmationJob = startCountUpTimer(
            scope = safeScope,
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
        offerAcceptanceConfirmationJob?.cancel()
        offerAcceptanceConfirmationJob = null
        _uiState.update {
            it.copy(offerAcceptanceConfirmationPercentage = 0f)
        }
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
        val timeToConfirmOfferAcceptance = with(Duration) { (1.5).seconds }
        val smoothTimerStep = with(Duration) { 50.milliseconds }
    }
}
