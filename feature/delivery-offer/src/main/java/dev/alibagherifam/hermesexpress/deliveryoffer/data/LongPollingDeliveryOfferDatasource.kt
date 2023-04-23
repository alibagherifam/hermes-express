package dev.alibagherifam.hermesexpress.deliveryoffer.data

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.data.RealTimeDeliveryOfferDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class LongPollingDeliveryOfferDatasource : RealTimeDeliveryOfferDatasource {
    // This is a Fake (Stub) implementation of long polling datasource
    override val incomingOfferStream: Flow<DeliveryOffer>
        get() = MutableSharedFlow()
}
