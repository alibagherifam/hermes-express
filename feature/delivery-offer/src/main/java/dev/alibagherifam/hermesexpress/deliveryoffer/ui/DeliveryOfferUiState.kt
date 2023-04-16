package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlin.time.Duration

data class DeliveryOfferUiState(
    val offer: DeliveryOffer? = null,
    val isAcceptingOfferInProgress: Boolean = false,
    val isOfferAccepted: Boolean = false,
    val offerTimeElapsed: Duration = Duration.ZERO
) {
    val isOfferExpired: Boolean
        get() = (offer != null) && (offerTimeElapsed == offer.timeToLive)
    val offerTimeElapsedPercentage: Float
        get() = if (offer == null) 0f else (offerTimeElapsed / offer.timeToLive).toFloat()
}