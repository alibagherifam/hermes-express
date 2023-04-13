package dev.alibagherifam.hermesexpress.fakeoffer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alibagherifam.hermesexpress.common.domain.Constants
import dev.alibagherifam.hermesexpress.common.domain.Offer
import dev.alibagherifam.hermesexpress.common.domain.generateFakeOrder
import dev.alibagherifam.hermesexpress.fakeoffer.data.CloudMessagingService
import dev.alibagherifam.hermesexpress.fakeoffer.data.RemoteMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToStringMap

class FakeOrderOfferViewModel(
    private val cloudMessagingService: CloudMessagingService
) : ViewModel() {
    private val _uiState = MutableStateFlow(FakeOrderOfferUiState())
    val uiState: StateFlow<FakeOrderOfferUiState> get() = _uiState

    fun broadcastFakeDeliveryOffer() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isOfferingInProgress = true)
            }
            sendDeliveryOfferMessage(generateFakeOrder())
            _uiState.update {
                it.copy(isOfferingInProgress = false)
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun sendDeliveryOfferMessage(offer: Offer) {
        val data = Properties.encodeToStringMap(offer)
        val receivers = "/topics/${Constants.TOPIC_DELIVERY_OFFER}"
        val message = RemoteMessage(to = receivers, data)
        cloudMessagingService.sendMessage(message)
    }
}
