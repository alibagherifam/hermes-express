package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.lifecycle.viewModelScope
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
    private var expireOfferJob: Job? = null

    init {
        repository.getOfferFlow().onEach { offer ->
            _uiState.update {
                it.copy(
                    offer = offer,
                    offerTimeElapsed = Duration.ZERO
                )
            }
            expireOfferJob?.cancel()
            if (offer != null) {
                startExpireOfferJob(offer)
            }
        }.launchIn(safeScope)
    }

    private fun startExpireOfferJob(offer: DeliveryOffer) {
        expireOfferJob = viewModelScope.launch {
            val step = with(Duration) { 50.milliseconds }
            val totalTime = offer.timeToLive
            var elapsedTime = Duration.ZERO
            while (elapsedTime < totalTime) {
                delay(step)
                elapsedTime += step
                elapsedTime.coerceAtMost(totalTime)
                _uiState.update { it.copy(offerTimeElapsed = elapsedTime) }
            }
            repository.removeOffer()
        }
    }

    fun acceptOffer() = safeLaunch {
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
}
