package dev.alibagherifam.hermesexpress.biker

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.addDeliveryOfferDestination
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.navigateToDeliveryOffer
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.addOfferingFakeDeliveryDestination
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R as DeliveryofferR
import dev.alibagherifam.hermesexpress.offeringfakedelivery.R as OfferingfakeDeliveryR

@Composable
fun MainNavHost(
    snackbarHostState: SnackbarHostState,
    onTerminalClick: (Terminal) -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "offering-fake-delivery"
) {
    var userMessage: Int? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    LaunchedEffect(key1 = userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(context.getString(it))
        }
    }
    NavHost(navController, startDestination) {
        addOfferingFakeDeliveryDestination(
            onFakeOfferSent = {
                userMessage = OfferingfakeDeliveryR.string.message_fake_offer_sent
                navController.navigateToDeliveryOffer()
            }
        )
        addDeliveryOfferDestination(
            onOfferAccepted = {
                userMessage = DeliveryofferR.string.message_offer_accepted
                navController.popBackStack()
            },
            onOfferExpired = {
                navController.popBackStack()
            },
            onTerminalClick
        )
    }
}
