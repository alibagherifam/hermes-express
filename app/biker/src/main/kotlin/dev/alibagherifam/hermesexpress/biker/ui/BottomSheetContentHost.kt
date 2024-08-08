package dev.alibagherifam.hermesexpress.biker.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.offeringScreen
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.OfferingFakeDelivery
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.offeringFakeDeliveryScreen
import kotlinx.coroutines.launch

@Composable
fun BottomSheetContentHost(
    offer: DeliveryOffer?,
    onTerminalClick: (Terminal) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = OfferingFakeDelivery
) {
    val scope = rememberCoroutineScope()
    if (offer != null) {
        LaunchedEffect(key1 = offer) {
            navController.navigate(dev.alibagherifam.hermesexpress.deliveryoffer.ui.Offering)
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        offeringFakeDeliveryScreen(
            onFakeOfferSent = {},
            onUserMessage = {
                scope.launch { snackbarHostState.showSnackbar(it) }
            }
        )
        offeringScreen(
            onOfferAccepted = {
                navController.popBackStack()
            },
            onOfferExpired = {
                navController.popBackStack()
            },
            onTerminalClick = onTerminalClick,
            onUserMessage = {
                scope.launch { snackbarHostState.showSnackbar(it) }
            }
        )
    }
}
