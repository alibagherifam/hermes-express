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
import dev.alibagherifam.hermesexpress.pushnotification.R
import dev.alibagherifam.hermesexpress.fakeoffer.ui.FakeOrderOfferingScreen
import dev.alibagherifam.hermesexpress.pushnotification.playNotificationSound
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(scaffoldState: BottomSheetScaffoldState) {
    var currentScreen by remember { mutableStateOf(1) }

    when (currentScreen) {
        /*
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            scope.launch {
                playNotificationSound(
                    context = context,
                    soundResId = R.raw.sfx_harp
                )
            }
            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
            currentScreen = 2
        */
        1 -> FakeOrderOfferingScreen()
        2 -> OrderDetails(
            order = generateFakeOrder(),
            onAcceptOfferClick = { currentScreen = 1 }
        )
        else -> error("Error")
    }
}
