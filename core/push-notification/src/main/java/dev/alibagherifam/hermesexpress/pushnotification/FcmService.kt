package dev.alibagherifam.hermesexpress.pushnotification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.alibagherifam.hermesexpress.common.data.DeliveryOfferStore
import dev.alibagherifam.hermesexpress.common.domain.Constants
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromStringMap

class FcmService : FirebaseMessagingService() {
    private val repository: DeliveryOfferRepository = DeliveryOfferStore

    override fun onNewToken(token: String) {
        Log.i(TAG, "onNewToken: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.isForTopic(Constants.TOPIC_DELIVERY_OFFER)) {
            require(remoteMessage.data.isNotEmpty())
            val payload = remoteMessage.data
            Log.i(TAG, "Message data payload: $payload")
            convertPayloadToDeliveryOffer(payload)?.let { offer ->
                repository.saveOffer(offer)
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun convertPayloadToDeliveryOffer(payload: Map<String, String>): DeliveryOffer? = try {
        Properties.decodeFromStringMap<DeliveryOffer>(payload)
    } catch (e: SerializationException) {
        Log.e(TAG, "Bad payload structure: ${e.message}")
        null
    }

    companion object {
        const val TAG = "CloudMessaging"
    }
}

fun RemoteMessage.isForTopic(topic: String): Boolean = (from == "/topics/$topic")
