package dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen

sealed interface DeliveryOfferEvent {
    data class AcceptOfferPressStateChange(val isPressed: Boolean) : DeliveryOfferEvent
    data class UserMessageShown(val message: String) : DeliveryOfferEvent
}
