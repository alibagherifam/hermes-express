package dev.alibagherifam.hermesexpress.common.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val id: Int,
    val terminals: List<Terminal>,
    val price: Float,
    val isBoxRequired: Boolean = true,
    val reverseLogistics: Boolean = false,
    val createdAt: Instant,
    val expireAt: Instant,
) {
    init {
        require(id > 0) { "Offer ID should be a positive number" }
        require(terminals.size > 1) { "Offer should contains at least 2 terminals" }
        require(price > 0f) { "Offer Price should be a positive number" }
        require(expireAt > createdAt) { "Offer's expiration should be after its creation" }
    }
}

val Offer.isExpired: Boolean get() = Clock.System.now() > expireAt
val Offer.origin: Terminal get() = terminals.first()
val Offer.designations: List<Terminal> get() = terminals.drop(1)

fun generateFakeDeliveryOffer() = Offer(
    id = 1,
    generateFakeTerminals(),
    price = 24.80f,
    createdAt = Instant.DISTANT_PAST,
    expireAt = Clock.System.now()
)
