package dev.alibagherifam.hermesexpress.cloudmessaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class CloudMessaging(context: Context) : CloudMessagingTokenDatasource {
    private val Context.dataStore by preferencesDataStore(name = "user-preferences")
    private val dataStore: DataStore<Preferences> = context.dataStore

    override suspend fun getToken(): String {
        val savedToken = getSavedToken()
        return if (savedToken != null) {
            Log.i(TAG, "Saved FCM token: $savedToken")
            savedToken
        } else {
            val newToken = Firebase.messaging.token.await()
            saveToken(newToken)
            Log.i(TAG, "New FCM updated: $newToken")
            newToken
        }
    }

    private suspend fun getSavedToken(): String? = dataStore.data
        .map { it[CLOUD_MESSAGING_TOKEN_KEY] }.first()

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[CLOUD_MESSAGING_TOKEN_KEY] = token
        }
    }

    suspend fun subscribeToDeliveryOfferTopic() {
        Firebase.messaging.subscribeToTopic(DELIVERY_OFFER_TOPIC).await()
    }

    fun createDeliveryOfferNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DELIVERY_OFFER_CHANNEL_ID,
                context.getString(R.string.label_delivery_offer_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = context.getString(
                R.string.message_delivery_offer_notification_channel_description
            )

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private val CLOUD_MESSAGING_TOKEN_KEY =
            stringPreferencesKey("cloud_messaging_token")

        private const val TAG = "cloud_messaging"
        private const val DELIVERY_OFFER_CHANNEL_ID = "channel_delivery_offer"
        private const val DELIVERY_OFFER_TOPIC = "delivery-offer"
    }
}
