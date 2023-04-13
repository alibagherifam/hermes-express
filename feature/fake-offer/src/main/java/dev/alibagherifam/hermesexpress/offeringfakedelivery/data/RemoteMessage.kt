package dev.alibagherifam.hermesexpress.offeringfakedelivery.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteMessage(
    val to: String,
    val data: Map<String, String>
)
