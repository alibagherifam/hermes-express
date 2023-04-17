package dev.alibagherifam.hermesexpress.offeringfakedelivery.data

import retrofit2.http.Body
import retrofit2.http.POST

internal interface CloudMessagingService {
    @POST("fcm/send")
    suspend fun sendMessage(@Body message: RemoteMessage)
}
