package dev.alibagherifam.hermesexpress.deliveryoffer.data

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class DeliveryOfferStore : DeliveryOfferRepository {
    private val _offer: MutableStateFlow<DeliveryOffer?> = MutableStateFlow(null)
    override fun getOfferStream(): StateFlow<DeliveryOffer?> = _offer
    override fun saveOffer(offer: DeliveryOffer) {
        _offer.value = offer
    }

    override suspend fun removeOffer() {
        _offer.value = null
    }

    override suspend fun acceptOffer() {
        _offer.value = null
    }
}
