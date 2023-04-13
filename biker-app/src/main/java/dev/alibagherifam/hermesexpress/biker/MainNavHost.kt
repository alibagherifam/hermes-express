package dev.alibagherifam.hermesexpress.biker

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import dev.alibagherifam.hermesexpress.order.domain.generateFakeOrder
import dev.alibagherifam.hermesexpress.order.ui.OrderDetails
import dev.alibagherifam.hermesexpress.pushnotification.fake.OfferOrder
import dev.alibagherifam.hermesexpress.pushnotification.playNotificationSound
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(scaffoldState: BottomSheetScaffoldState) {
    var currentScreen by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    when (currentScreen) {
        1 -> OfferOrder(
            onOfferOrderClick = {
                scope.launch {
                    playNotificationSound(
                        context = context,
                        soundResId = dev.alibagherifam.hermesexpress.pushnotification.R.raw.sfx_harp
                    )
                }
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
                currentScreen = 2
            }
        )
        2 -> OrderDetails(
            order = generateFakeOrder(),
            onAcceptOfferClick = { currentScreen = 1 }
        )
        else -> error("Error")
    }
}
