package dev.alibagherifam.hermesexpress.cloudmessaging

import android.os.Bundle
import dev.alibagherifam.hermesexpress.common.data.RealTimeDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromStringMap

private typealias PayloadMap = Map<String, String>

class CloudMessagingDeliveryOfferDatasource : RealTimeDeliveryOfferDatasource {

    private val _incomingOfferStream = MutableSharedFlow<DeliveryOffer>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 1
    )

    override val incomingOfferStream: Flow<DeliveryOffer>
        get() = _incomingOfferStream

    fun handleMessagePayload(payload: PayloadMap) {
        convertPayloadToDeliveryOffer(payload)?.let { offer ->
            _incomingOfferStream.tryEmit(offer)
        }
    }

    fun handleMessagePayload(payload: Bundle) {
        val mapPayload = convertBundleToMapPayload(payload)
        handleMessagePayload(mapPayload)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun convertPayloadToDeliveryOffer(payload: PayloadMap): DeliveryOffer? = try {
        Properties.decodeFromStringMap<DeliveryOffer>(payload)
    } catch (e: SerializationException) {
        println("I cant deserialize!")
        null
    }

    // TODO: Perhaps it would be better to store the serialized Offer
    //  using a single key, rather than flattening all of its properties.
    //  This would help to prevent conflicts with FCM keys.
    private fun convertBundleToMapPayload(bundlePayload: Bundle): PayloadMap {
        val mapPayload = mutableMapOf<String, String>()
        val specialKeys = listOf(
            "collapse_key",
            "from",
            "gcm.n.analytics_data"
        )
        bundlePayload.keySet().filterNot { key ->
            key.contains("google") || key in specialKeys
        }.forEach { key ->
            val value = checkNotNull(bundlePayload.getString(key))
            mapPayload[key] = value
        }
        println("I cant deserialize!")
        return mapPayload
    }
}
