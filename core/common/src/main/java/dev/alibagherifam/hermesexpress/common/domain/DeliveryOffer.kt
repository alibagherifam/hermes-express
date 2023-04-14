package dev.alibagherifam.hermesexpress.common.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.time.Duration

@Serializable
data class DeliveryOffer(
    val id: Int,
    val terminals: List<Terminal>,
    val price: Float,
    val isBoxRequired: Boolean = true,
    val reverseLogistics: Boolean = false,
    val timeToLive: Duration,
) {
    @Transient
    val createdAt: Instant = Clock.System.now()

    init {
        require(id > 0) { "Negative ID is not allowed" }
        require(terminals.size > 1) { "At least 2 terminals needed" }
        require(price > 0f) { "Negative Price is not allowed" }
    }
}

val DeliveryOffer.isExpired: Boolean get() = Clock.System.now() > createdAt + timeToLive
val DeliveryOffer.origin: Terminal get() = terminals.first()
val DeliveryOffer.destinations: List<Terminal> get() = terminals.drop(1)

fun generateFakeDeliveryOffer() = DeliveryOffer(
    id = 1,
    terminals = generateFakeTerminals(),
    price = 24.80f,
    timeToLive = with(Duration) { 10.seconds }
)
