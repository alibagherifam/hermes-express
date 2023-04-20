package dev.alibagherifam.hermesexpress.biker.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.addDeliveryOfferScreen
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.navigateToDeliveryOffer
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.addOfferingFakeDeliveryScreen
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.offeringFakeDeliveryRoute
import kotlinx.coroutines.launch

@Composable
fun BottomSheetContentHost(
    snackbarHostState: SnackbarHostState,
    onTerminalClick: (Terminal) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = offeringFakeDeliveryRoute
) {
    val scope = rememberCoroutineScope()
    NavHost(navController, startDestination, modifier) {
        addOfferingFakeDeliveryScreen(
            onFakeOfferSent = {
                navController.navigateToDeliveryOffer()
            },
            onUserMessage = {
                scope.launch { snackbarHostState.showSnackbar(it) }
            }
        )
        addDeliveryOfferScreen(
            onOfferAccepted = {
                navController.popBackStack()
            },
            onOfferExpired = {
                navController.popBackStack()
            },
            onTerminalClick,
            onUserMessage = {
                scope.launch { snackbarHostState.showSnackbar(it) }
            }
        )
    }
}
