package dev.alibagherifam.hermesexpress.cloudmessaging

import android.content.Context
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

internal class CloudMessaging(context: Context) : CloudMessagingTokenDatasource {
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
        .map { it[KEY_CLOUD_MESSAGING_TOKEN] }.first()

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_CLOUD_MESSAGING_TOKEN] = token
        }
    }

    companion object {
        private val KEY_CLOUD_MESSAGING_TOKEN =
            stringPreferencesKey("cloud_messaging_token")

        private const val TAG = "cloud-messaging"
    }
}
