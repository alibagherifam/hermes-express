package dev.alibagherifam.hermesexpress.cloudmessaging

import dev.alibagherifam.hermesexpress.common.data.RealTimeDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromStringMap

class CloudMessagingDeliveryOfferDatasource : RealTimeDeliveryOfferDatasource {

    private val _incomingOfferStream = MutableSharedFlow<DeliveryOffer>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    override val incomingOfferStream: Flow<DeliveryOffer>
        get() = _incomingOfferStream

    fun handleMessagePayload(payload: Map<String, String>) {
        convertPayloadToDeliveryOffer(payload)?.let { offer ->
            _incomingOfferStream.tryEmit(offer)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun convertPayloadToDeliveryOffer(payload: Map<String, String>): DeliveryOffer? = try {
        Properties.decodeFromStringMap<DeliveryOffer>(payload)
    } catch (e: SerializationException) {
        null
    }
}
