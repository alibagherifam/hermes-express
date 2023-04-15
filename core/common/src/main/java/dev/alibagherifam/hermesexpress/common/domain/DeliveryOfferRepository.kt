package dev.alibagherifam.hermesexpress.common.domain

import kotlinx.coroutines.flow.StateFlow

interface DeliveryOfferRepository {
    fun getOfferFlow(): StateFlow<DeliveryOffer?>
    fun saveOffer(offer: DeliveryOffer)
    suspend fun acceptOffer()
}
