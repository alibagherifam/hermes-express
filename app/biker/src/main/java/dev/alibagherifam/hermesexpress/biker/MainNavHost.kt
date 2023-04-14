package dev.alibagherifam.hermesexpress.biker

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import dev.alibagherifam.hermesexpress.common.data.DeliveryOfferStore
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.DeliveryOfferScreen
import dev.alibagherifam.hermesexpress.map.MapState
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.OfferingFakeDeliveryScreen
import dev.alibagherifam.hermesexpress.pushnotification.R
import dev.alibagherifam.hermesexpress.pushnotification.playNotificationSound
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    mapState: MapState,
    scaffoldState: BottomSheetScaffoldState,
) {
    val repository: DeliveryOfferRepository = DeliveryOfferStore
    val offer by repository.offer.collectAsState()
    LaunchedEffect(key1 = offer) {
        launch {
            scaffoldState.bottomSheetState.expand()
        }
    }
    if (offer == null) {
        OfferingFakeDeliveryScreen()
    } else {
        val safeOffer = requireNotNull(offer)
        mapState.updateMarkerCoordinates(
            safeOffer.terminals.map { Pair(it.latitude, it.longitude) }
        )
        DeliveryOfferScreen(
            safeOffer,
            onAcceptOfferClick = {
                repository.clearSavedOffer()
            }
        )
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            launch {
                playNotificationSound(
                    context = context,
                    soundResId = R.raw.sfx_harp
                )
            }
        }
    }
}
