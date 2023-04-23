package dev.alibagherifam.hermesexpress.common.data

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlinx.coroutines.flow.Flow

interface RealTimeDeliveryOfferDatasource {
    val incomingOfferStream: Flow<DeliveryOffer>
}
