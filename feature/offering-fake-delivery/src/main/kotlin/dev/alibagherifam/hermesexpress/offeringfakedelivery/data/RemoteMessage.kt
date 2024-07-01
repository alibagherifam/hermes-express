package dev.alibagherifam.hermesexpress.offeringfakedelivery.data

import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteMessage(
    val to: String,
    val data: Map<String, String>
)
