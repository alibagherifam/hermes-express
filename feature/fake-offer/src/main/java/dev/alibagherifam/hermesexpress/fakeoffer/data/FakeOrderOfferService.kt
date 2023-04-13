package dev.alibagherifam.hermesexpress.fakeoffer.data

import dev.alibagherifam.hermesexpress.common.domain.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface FakeOrderOfferingService {
    @POST("fcm/send")
    suspend fun sendFakeOrderMessage(@Body message: FakeOrderOffer)
}

suspend fun FakeOrderOfferingService.broadcastFakeOrder(order: OrderDto) {
    val offer = FakeOrderOffer(
        to = "/topics/${Constants.TOPIC_BIKERS}",
        data = order
    )
    sendFakeOrderMessage(offer)
}
