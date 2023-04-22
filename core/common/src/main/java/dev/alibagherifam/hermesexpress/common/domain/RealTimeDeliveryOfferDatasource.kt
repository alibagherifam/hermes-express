package dev.alibagherifam.hermesexpress.common.domain

import kotlinx.coroutines.flow.Flow

interface RealTimeDeliveryOfferDatasource {
    val incomingOfferStream: Flow<DeliveryOffer>
}
