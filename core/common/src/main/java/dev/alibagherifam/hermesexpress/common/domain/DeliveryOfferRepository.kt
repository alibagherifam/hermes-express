package dev.alibagherifam.hermesexpress.common.domain

import kotlinx.coroutines.flow.StateFlow

interface DeliveryOfferRepository {
    fun getOfferStream(): StateFlow<DeliveryOffer?>
    fun saveOffer(offer: DeliveryOffer)
    suspend fun removeOffer()
    suspend fun acceptOffer()
}
