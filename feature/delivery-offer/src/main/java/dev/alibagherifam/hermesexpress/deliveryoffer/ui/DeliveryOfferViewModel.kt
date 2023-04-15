package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeliveryOfferViewModel(
    private val repository: DeliveryOfferRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DeliveryOfferUiState())
    val uiState: StateFlow<DeliveryOfferUiState> get() = _uiState

    init {
        _uiState.update {
            it.copy(offer = repository.offer.value)
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
}
