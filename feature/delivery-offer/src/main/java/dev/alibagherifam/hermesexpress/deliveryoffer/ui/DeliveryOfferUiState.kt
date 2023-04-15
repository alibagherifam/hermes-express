package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer

data class DeliveryOfferUiState(
    val offer: DeliveryOffer? = null,
    val isAcceptingOfferInProgress: Boolean = false,
    val isOfferAccepted: Boolean = false
)
