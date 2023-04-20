package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen.OfferingFakeDeliveryEvent
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen.OfferingFakeDeliveryScreen
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen.OfferingFakeDeliveryViewModel
import org.koin.androidx.compose.koinViewModel

const val offeringFakeDeliveryRoute = "offering-fake-delivery"

fun NavGraphBuilder.addOfferingFakeDeliveryScreen(
    onFakeOfferSent: () -> Unit,
    onUserMessage: (String) -> Unit
) {
    composable(offeringFakeDeliveryRoute) {
        val viewModel: OfferingFakeDeliveryViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        OfferingFakeDeliveryScreen(
            uiState,
            onSendFakeOffer = {
                viewModel.onNewEvent(
                    OfferingFakeDeliveryEvent.BroadcastFakeDeliveryRequested
                )
            }
        )
        uiState.userMessages.firstOrNull()?.let { message ->
            LaunchedEffect(key1 = message) {
                onUserMessage(message)
                viewModel.onNewEvent(
                    OfferingFakeDeliveryEvent.UserMessageShown(message)
                )
            }
        }
        if (uiState.isFakeOfferSent) {
            LaunchedEffect(key1 = Unit) {
                onFakeOfferSent()
                viewModel.consumeIsFakeOfferSent()
            }
        }
    }
}

fun NavController.navigateToOfferingFakeDelivery() {
    navigate(offeringFakeDeliveryRoute)
}
