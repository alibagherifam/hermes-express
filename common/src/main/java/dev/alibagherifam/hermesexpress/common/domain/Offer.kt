package dev.alibagherifam.hermesexpress.common.domain

import java.time.Instant

@kotlinx.serialization.Serializable
data class Order(
    val id: Int,
    val terminals: List<Terminal>,
    val price: Float,
    val isBoxRequired: Boolean = true,
    val reverseLogistics: Boolean = false,
    val createdAt: Instant,
    val expireAt: Instant,
) {
    init {
        require(id > 0) { "Order ID should be a positive number" }
        require(terminals.size > 1) { "Order should contains at least 2 terminals" }
        require(price > 0f) { "Order Price should be a positive number" }
        require(expireAt > createdAt) { "Order's expiration should be after its creation" }
    }
}

val Order.isExpired: Boolean get() = Instant.now() > expireAt
val Order.origin: Terminal get() = terminals.first()
val Order.designations: List<Terminal> get() = terminals.drop(1)

fun generateFakeOrder() = Order(
    id = 1,
    generateFakeTerminals(),
    price = 24.80f,
    createdAt = Instant.EPOCH,
    expireAt = Instant.now()
)
