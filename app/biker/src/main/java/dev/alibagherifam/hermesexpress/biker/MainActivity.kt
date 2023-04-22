package dev.alibagherifam.hermesexpress.biker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.alibagherifam.hermesexpress.biker.ui.MainScreen
import dev.alibagherifam.hermesexpress.cloudmessaging.subscribeForDeliveryOfferMessages
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HermesTheme {
                LaunchedEffect(key1 = Unit) { subscribeForDeliveryOfferMessages() }
                val repository: DeliveryOfferRepository = koinInject()
                val offer by repository.receivedOffer.collectAsState()
                MainScreen(
                    offer,
                    onLocationPermissionDeny = { finish() }
                )
            }
        }
    }
}
