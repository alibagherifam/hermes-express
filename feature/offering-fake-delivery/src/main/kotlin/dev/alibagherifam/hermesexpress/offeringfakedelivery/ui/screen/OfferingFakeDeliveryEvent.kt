package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen

sealed interface OfferingFakeDeliveryEvent {
    data object BroadcastFakeDeliveryRequested : OfferingFakeDeliveryEvent
    data class UserMessageShown(val message: String) : OfferingFakeDeliveryEvent
}
