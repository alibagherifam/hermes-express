package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alibagherifam.hermesexpress.common.domain.Constants
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.CloudMessagingService
import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.RemoteMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToStringMap

class OfferingFakeDeliveryViewModel(
    private val cloudMessagingService: CloudMessagingService
) : ViewModel() {
    private val _uiState = MutableStateFlow(OfferingFakeDeliveryUiState())
    val uiState: StateFlow<OfferingFakeDeliveryUiState> get() = _uiState

    fun broadcastFakeDeliveryOffer() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isOfferingInProgress = true)
            }
            sendDeliveryOfferMessage(generateFakeDeliveryOffer())
            _uiState.update {
                it.copy(
                    isOfferingInProgress = false,
                    isFakeOfferSent = true
                )
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun sendDeliveryOfferMessage(offer: DeliveryOffer) {
        val data = Properties.encodeToStringMap(offer)
        val receivers = "/topics/${Constants.TOPIC_DELIVERY_OFFER}"
        val message = RemoteMessage(to = receivers, data)
        cloudMessagingService.sendMessage(message)
    }

    fun resetState() {
        _uiState.update { OfferingFakeDeliveryUiState() }
    }
}
