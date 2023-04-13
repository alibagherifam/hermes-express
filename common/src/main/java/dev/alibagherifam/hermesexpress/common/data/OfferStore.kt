package dev.alibagherifam.hermesexpress.common.data

import dev.alibagherifam.hermesexpress.common.domain.Offer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object OfferStore {
    private val _offer: MutableStateFlow<Offer?> = MutableStateFlow(null)
    val offer: StateFlow<Offer?> get() = _offer
    fun saveOffer(offer: Offer?) {
        _offer.value = offer
    }
}
