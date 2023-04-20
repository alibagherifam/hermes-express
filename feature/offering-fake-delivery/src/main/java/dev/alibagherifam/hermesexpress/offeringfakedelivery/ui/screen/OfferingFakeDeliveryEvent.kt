package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen

sealed interface OfferingFakeDeliveryEvent {
    object BroadcastFakeDeliveryRequested : OfferingFakeDeliveryEvent
    data class UserMessageShown(val message: String) : OfferingFakeDeliveryEvent
}
