package dev.alibagherifam.hermesexpress.biker

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import dev.alibagherifam.hermesexpress.common.data.OfferStore
import dev.alibagherifam.hermesexpress.common.domain.generateFakeOrder
import dev.alibagherifam.hermesexpress.fakeoffer.ui.FakeOrderOfferScreen
import dev.alibagherifam.hermesexpress.order.ui.OrderDetails
import dev.alibagherifam.hermesexpress.pushnotification.R
import dev.alibagherifam.hermesexpress.pushnotification.playNotificationSound
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(scaffoldState: BottomSheetScaffoldState) {
    val offer by OfferStore.offer.collectAsState()
    if (offer == null) {
        FakeOrderOfferScreen()
    } else {
        OrderDetails(
            order = generateFakeOrder(),
            onAcceptOfferClick = {
                OfferStore.saveOffer(null)
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
            launch {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }
}
