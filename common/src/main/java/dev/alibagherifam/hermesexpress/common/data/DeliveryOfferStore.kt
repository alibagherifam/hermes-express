package dev.alibagherifam.hermesexpress.common.data

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object DeliveryOfferStore {
    private val _offer: MutableStateFlow<DeliveryOffer?> = MutableStateFlow(null)
    val offer: StateFlow<DeliveryOffer?> get() = _offer
    fun saveOffer(offer: DeliveryOffer?) {
        _offer.value = offer
    }
}
