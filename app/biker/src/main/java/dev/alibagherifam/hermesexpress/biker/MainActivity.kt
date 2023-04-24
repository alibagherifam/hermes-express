package dev.alibagherifam.hermesexpress.biker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.alibagherifam.hermesexpress.biker.ui.MainScreen
import dev.alibagherifam.hermesexpress.cloudmessaging.CloudMessagingDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.DeliveryOfferRepository
import org.koin.android.ext.android.get
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleNotificationPayload()
        setContent {
            HermesTheme {
                val repository: DeliveryOfferRepository = koinInject()
                val offer by repository.receivedOffer.collectAsState()
                MainScreen(
                    offer,
                    onLocationPermissionDeny = { finish() }
                )
            }
        }
    }

    private fun handleNotificationPayload() {
        intent.extras?.let { payload ->
            val deliveryOfferDatasource: CloudMessagingDeliveryOfferDatasource = get()
            deliveryOfferDatasource.handleMessagePayload(payload)
        }
    }
}
