package dev.alibagherifam.hermesexpress.offeringfakedelivery.data

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface CloudMessagingService {
    @Headers("Authorization: key=$FIREBASE_API_KEY")
    @POST("https://fcm.googleapis.com/fcm/send")
    suspend fun sendMessage(@Body message: RemoteMessage)
}
