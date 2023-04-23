package dev.alibagherifam.hermesexpress.offeringfakedelivery.data

import dev.alibagherifam.hermesexpress.cloudmessaging.CloudMessagingTokenDatasource
import dev.alibagherifam.hermesexpress.common.data.LocationProvider
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import kotlinx.coroutines.flow.first
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToStringMap

internal class OfferingFakeDeliveryRepository(
    private val cloudMessagingService: CloudMessagingService,
    private val cloudMessagingTokenDatasource: CloudMessagingTokenDatasource,
    private val locationProvider: LocationProvider
) {
    suspend fun broadcastFakeDeliveryOffer(stringProvider: StringProvider) {
        val userLocation = locationProvider.getUserLocationStream().first()
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
