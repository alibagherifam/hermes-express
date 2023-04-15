package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration

class DeliveryOfferViewModel(
    private val repository: DeliveryOfferRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DeliveryOfferUiState())
    val uiState: StateFlow<DeliveryOfferUiState> get() = _uiState

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
        }.launchIn(viewModelScope)
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
        }
    }

    fun acceptOffer() {
        viewModelScope.launch {
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
    }

    fun resetState() {
        _uiState.update { DeliveryOfferUiState() }
    }
}
