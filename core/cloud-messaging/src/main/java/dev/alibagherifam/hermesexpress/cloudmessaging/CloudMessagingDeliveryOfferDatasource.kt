package dev.alibagherifam.hermesexpress.cloudmessaging

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.RealTimeDeliveryOfferDatasource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class CloudMessagingDeliveryOfferDatasource : RealTimeDeliveryOfferDatasource {

    private val _incomingOfferStream = MutableSharedFlow<DeliveryOffer>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    override val incomingOfferStream: Flow<DeliveryOffer>
        get() = _incomingOfferStream

    fun emitOffer(offer: DeliveryOffer) {
        _incomingOfferStream.tryEmit(offer)
    }
}
