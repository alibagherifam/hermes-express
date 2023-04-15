package dev.alibagherifam.hermesexpress.biker

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.DeliveryOfferDestination
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.OfferingFakeDeliveryDestination
import dev.alibagherifam.hermesexpress.pushnotification.playNotificationSound
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R as DeliveryofferR
import dev.alibagherifam.hermesexpress.offeringfakedelivery.R as OfferingfakeDeliveryR
import dev.alibagherifam.hermesexpress.pushnotification.R as PushnotificationR

@Composable
fun MainNavHost(snackbarHostState: SnackbarHostState) {
    var userMessage: Int? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    LaunchedEffect(key1 = userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(context.getString(it))
        }
    }
    var backStack by remember { mutableStateOf(1) }
    when (backStack) {
        1 -> {
            OfferingFakeDeliveryDestination(
                onFakeOfferSent = {
                    backStack = 2
                    userMessage = OfferingfakeDeliveryR.string.message_fake_offer_sent
                }
            )
        }
        2 -> {
            DeliveryOfferDestination(
                onOfferAccepted = {
                    backStack = 1
                    userMessage = DeliveryofferR.string.message_offer_accepted
                },
                onTerminalClick = {
                    TODO()
                }
            )
            LaunchedEffect(key1 = Unit) {
                playNotificationSound(
                    context = context,
                    soundResId = PushnotificationR.raw.sfx_harp
                )
            }
        }
        else -> error("Unknown destination")
    }
}
