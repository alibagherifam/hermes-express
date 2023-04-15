package dev.alibagherifam.hermesexpress.common.data

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DeliveryOfferStore : DeliveryOfferRepository {
    private val _offer: MutableStateFlow<DeliveryOffer?> = MutableStateFlow(null)
    override val offer: StateFlow<DeliveryOffer?> get() = _offer
    override fun saveOffer(offer: DeliveryOffer) {
        _offer.value = offer
    }

    override suspend fun acceptOffer() {
        _offer.value = null
    }
}
