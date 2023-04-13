package dev.alibagherifam.hermesexpress.fakeoffer.data

import kotlinx.serialization.Serializable

@Serializable
data class FakeOrderOffer(
    val to: String,
    val data: OrderDto
)
