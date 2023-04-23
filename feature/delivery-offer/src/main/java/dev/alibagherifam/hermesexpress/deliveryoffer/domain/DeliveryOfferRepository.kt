package dev.alibagherifam.hermesexpress.deliveryoffer.domain

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlinx.coroutines.flow.StateFlow

interface DeliveryOfferRepository {
    val receivedOffer: StateFlow<DeliveryOffer?>
    suspend fun acceptOffer()
    suspend fun ignoreOffer()
    fun tryIgnoreOffer()
}
