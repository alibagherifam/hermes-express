package dev.alibagherifam.hermesexpress.offeringfakedelivery.data

import dev.alibagherifam.hermesexpress.cloudmessaging.CloudMessagingTokenDatasource
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.LatLong
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToStringMap

internal class OfferingFakeDeliveryRepository(
    private val cloudMessagingService: CloudMessagingService,
    private val cloudMessagingTokenDatasource: CloudMessagingTokenDatasource
) {
    suspend fun broadcastFakeDeliveryOffer(stringProvider: StringProvider) {
        // TODO: Get this value from Location Provider
        val userLocation = LatLong(35.9818, 50.7387)
        val fakeOffer = generateFakeDeliveryOffer(stringProvider, userLocation)
        sendDeliveryOfferMessage(fakeOffer)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun sendDeliveryOfferMessage(offer: DeliveryOffer) {
        val message = RemoteMessage(
            to = cloudMessagingTokenDatasource.getToken(),
            data = Properties.encodeToStringMap(offer)
        )
        cloudMessagingService.sendMessage(message)
    }
}
