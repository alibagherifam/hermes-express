package dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen

import kotlin.time.Duration

internal data class DeliveryOfferUiState(
    val offer: DeliveryOfferUiModel? = null,
    val offerAcceptanceConfirmationPercentage: Float = 0f,
    val isAcceptingOfferInProgress: Boolean = false,
    val isOfferAccepted: Boolean = false,
    val offerTimeElapsed: Duration = Duration.ZERO,
    val isOfferExpired: Boolean = false,
    val userMessages: List<String> = emptyList()
) {
    val offerTimeElapsedPercentage: Float
        get() = if (offer == null) 0f else (offerTimeElapsed / offer.timeToLive).toFloat()
}
