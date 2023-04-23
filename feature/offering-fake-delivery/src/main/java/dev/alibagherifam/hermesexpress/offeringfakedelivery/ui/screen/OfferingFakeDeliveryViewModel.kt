package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen

import dev.alibagherifam.hermesexpress.common.ui.BaseViewModel
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import dev.alibagherifam.hermesexpress.offeringfakedelivery.R
import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.OfferingFakeDeliveryRepository
import kotlinx.coroutines.flow.update
import dev.alibagherifam.hermesexpress.common.R as CommonR

internal class OfferingFakeDeliveryViewModel(
    private val repository: OfferingFakeDeliveryRepository,
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
        repository.broadcastFakeDeliveryOffer()
        _uiState.update {
            val message = stringProvider.getString(R.string.message_fake_offer_sent)
            it.copy(
                isOfferingInProgress = false,
                isFakeOfferSent = true,
                userMessages = it.userMessages + message
            )
        }
    }

    fun consumeIsFakeOfferSent() {
        _uiState.update {
            it.copy(isFakeOfferSent = false)
        }
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
