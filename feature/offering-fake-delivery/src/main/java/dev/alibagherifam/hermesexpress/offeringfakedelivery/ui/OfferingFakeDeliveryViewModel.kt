package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import dev.alibagherifam.hermesexpress.common.domain.Constants
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.ui.BaseViewModel
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import dev.alibagherifam.hermesexpress.offeringfakedelivery.R
import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.CloudMessagingService
import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.RemoteMessage
import kotlinx.coroutines.flow.update
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToStringMap
import dev.alibagherifam.hermesexpress.common.R as CommonR

internal class OfferingFakeDeliveryViewModel(
    private val cloudMessagingService: CloudMessagingService,
    private val stringProvider: StringProvider
) : BaseViewModel<OfferingFakeDeliveryUiState>(
    initialState = OfferingFakeDeliveryUiState()
) {
    fun onNewEvent(event: OfferingFakeDeliveryEvent) {
        when (event) {
            is OfferingFakeDeliveryEvent.BroadcastFakeDeliveryRequested -> {
                broadcastFakeDeliveryOffer()
            }

            is OfferingFakeDeliveryEvent.UserMessageShown -> {
                consumeUserMessage(event.message)
            }
        }
    }

    private fun broadcastFakeDeliveryOffer() = safeLaunch {
        _uiState.update {
            it.copy(isOfferingInProgress = true)
        }
        sendDeliveryOfferMessage(
            offer = generateFakeDeliveryOffer(stringProvider)
        )
        val message = stringProvider.getString(R.string.message_fake_offer_sent)
        _uiState.update {
            it.copy(
                isOfferingInProgress = false,
                isFakeOfferSent = true,
                userMessages = it.userMessages + message
            )
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun sendDeliveryOfferMessage(offer: DeliveryOffer) {
        val data = Properties.encodeToStringMap(offer)
        val receivers = "/topics/${Constants.TOPIC_DELIVERY_OFFER}"
        val message = RemoteMessage(to = receivers, data)
        cloudMessagingService.sendMessage(message)
    }

    fun resetState() {
        _uiState.update { OfferingFakeDeliveryUiState() }
    }

    override fun handleIOException(exception: Throwable) {
        val errorMessage = stringProvider.getString(CommonR.string.message_generic_io_error)
        _uiState.update { oldState ->
            oldState.copy(
                userMessages = oldState.userMessages + errorMessage
            )
        }
    }

    private fun consumeUserMessage(message: String) {
        _uiState.update { oldState ->
            oldState.copy(
                userMessages = oldState.userMessages.filterNot { it == message },
                isOfferingInProgress = false
            )
        }
    }
}
