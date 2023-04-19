package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlin.time.Duration

internal data class DeliveryOfferUiState(
    val offer: DeliveryOffer? = null,
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
