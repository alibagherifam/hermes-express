package dev.alibagherifam.hermesexpress.common.data

import dev.alibagherifam.hermesexpress.common.domain.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object OfferStore {
    private val _offer: MutableStateFlow<Order?> = MutableStateFlow(null)
    val offer: StateFlow<Order?> get() = _offer
    fun saveOffer(offer: Order?) {
        _offer.value = offer
    }
}
