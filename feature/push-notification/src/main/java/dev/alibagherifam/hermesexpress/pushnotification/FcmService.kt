package dev.alibagherifam.hermesexpress.pushnotification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.alibagherifam.hermesexpress.common.data.OfferStore
import dev.alibagherifam.hermesexpress.common.domain.Constants
import dev.alibagherifam.hermesexpress.common.domain.Offer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromStringMap

class FcmService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.i(TAG, "onNewToken: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.isForTopic(Constants.TOPIC_DELIVERY_OFFER)) {
            require(remoteMessage.data.isNotEmpty())
            val payload = remoteMessage.data
            Log.i(TAG, "Message data payload: $payload")
            convertPayloadToOffer(payload)?.let { offer ->
                OfferStore.saveOffer(offer)
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun convertPayloadToOffer(payload: Map<String, String>): Offer? = try {
        Properties.decodeFromStringMap<Offer>(payload)
    } catch (e: SerializationException) {
        Log.e(TAG, "Bad payload structure: ${e.message}")
        null
    }

    companion object {
        const val TAG = "CloudMessaging"
    }
}

fun RemoteMessage.isForTopic(topic: String): Boolean = (from == "/topics/$topic")
