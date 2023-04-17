package dev.alibagherifam.hermesexpress.common.domain

import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import kotlinx.serialization.Serializable
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
    init {
        require(id > 0) { "Negative ID is not allowed" }
        require(terminals.size > 1) { "At least 2 terminals needed" }
        require(price > 0f) { "Negative Price is not allowed" }
    }
}

fun generateFakeDeliveryOffer(stringProvider: StringProvider) = DeliveryOffer(
    id = 1,
    terminals = generateFakeTerminals(stringProvider),
    price = 24.80f,
    timeToLive = with(Duration) { 10.seconds }
)
