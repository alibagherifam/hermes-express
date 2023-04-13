package dev.alibagherifam.hermesexpress.pushnotification

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dev.alibagherifam.hermesexpress.common.domain.Constants
import kotlinx.coroutines.tasks.await

suspend fun subscribeForDeliveryOfferMessages() {
    Firebase.messaging.subscribeToTopic(Constants.TOPIC_DELIVERY_OFFER).await()
}
