package dev.alibagherifam.hermesexpress.common.domain

import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class DeliveryOffer(
    val id: Int,
    val terminals: List<Terminal>,
    val earnings: Float,
    val timeToLive: Duration,
    val estimatedDeliveryTime: Duration,
    val isShipmentFragile: Boolean = true,
    val isBoxRequired: Boolean = true,
    val reverseLogistics: Boolean = false
) {
    init {
        require(id > 0) { "Negative ID is not allowed" }
        require(terminals.size > 1) { "At least 2 terminals needed" }
        require(earnings > 0f) { "Negative earnings is not allowed" }
        require(timeToLive.isPositive()) { "Negative TTL is not allowed" }
        require(estimatedDeliveryTime.isPositive()) { "Negative delivery time is not allowed" }
    }
}

fun generateFakeDeliveryOffer(
    stringProvider: StringProvider,
    userCoordinates: LatLong = LatLong(35.7194, 51.3709)
) = DeliveryOffer(
    id = 1,
    terminals = generateFakeTerminals(stringProvider, userCoordinates),
    earnings = 24.80f,
    timeToLive = with(Duration) { 20.seconds },
    estimatedDeliveryTime = with(Duration) { 35.minutes }
)
