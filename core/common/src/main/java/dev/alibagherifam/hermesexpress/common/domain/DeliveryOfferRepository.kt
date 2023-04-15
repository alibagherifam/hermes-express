package dev.alibagherifam.hermesexpress.common.domain

import kotlinx.coroutines.flow.StateFlow

interface DeliveryOfferRepository {
    val offer: StateFlow<DeliveryOffer?>
    fun saveOffer(offer: DeliveryOffer)
    suspend fun acceptOffer()
}
