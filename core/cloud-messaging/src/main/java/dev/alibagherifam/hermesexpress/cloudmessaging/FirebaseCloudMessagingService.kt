package dev.alibagherifam.hermesexpress.cloudmessaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.alibagherifam.hermesexpress.common.domain.Constants
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromStringMap
import org.koin.android.ext.android.inject

internal class FirebaseCloudMessagingService : FirebaseMessagingService() {
    private val deliveryOfferDatasource: CloudMessagingDeliveryOfferDatasource by inject()
    private val cloudMessagingTokenStore: CloudMessagingTokenStore by inject()

    private val job = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onNewToken(token: String) {
        scope.launch {
            cloudMessagingTokenStore.saveToken(token)
        }
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.isForTopic(Constants.TOPIC_DELIVERY_OFFER)) {
            require(remoteMessage.data.isNotEmpty())
            val payload = remoteMessage.data
            convertPayloadToDeliveryOffer(payload)?.let { offer ->
                deliveryOfferDatasource.emitOffer(offer)
            }
        }
    }

    private fun RemoteMessage.isForTopic(topic: String): Boolean = (from == "/topics/$topic")

    @OptIn(ExperimentalSerializationApi::class)
    private fun convertPayloadToDeliveryOffer(payload: Map<String, String>): DeliveryOffer? = try {
        Properties.decodeFromStringMap<DeliveryOffer>(payload)
    } catch (e: SerializationException) {
        null
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
