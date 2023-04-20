package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen

internal data class OfferingFakeDeliveryUiState(
    val isOfferingInProgress: Boolean = false,
    val isFakeOfferSent: Boolean = false,
    val userMessages: List<String> = emptyList()
)
