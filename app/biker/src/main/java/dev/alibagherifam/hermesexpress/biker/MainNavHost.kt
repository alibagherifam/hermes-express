package dev.alibagherifam.hermesexpress.biker

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.DeliveryOfferScreen
import dev.alibagherifam.hermesexpress.map.MapState
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.OfferingFakeDeliveryScreen
import dev.alibagherifam.hermesexpress.pushnotification.R
import dev.alibagherifam.hermesexpress.pushnotification.playNotificationSound
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    offer: DeliveryOffer?,
    mapState: MapState,
    scaffoldState: BottomSheetScaffoldState,
    onAcceptOfferClick: () -> Unit,
) {
    LaunchedEffect(key1 = offer) {
        scaffoldState.bottomSheetState.expand()
    }
    if (offer == null) {
        OfferingFakeDeliveryScreen()
    } else {
        mapState.updateMarkerCoordinates(
            offer.terminals.map { Pair(it.latitude, it.longitude) }
        )
        DeliveryOfferScreen(
            offer,
            onAcceptOfferClick,
            onTerminalClick = {
                TODO()
            }
        )
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            playNotificationSound(
                context = context,
                soundResId = R.raw.sfx_harp
            )
        }
    }
}
