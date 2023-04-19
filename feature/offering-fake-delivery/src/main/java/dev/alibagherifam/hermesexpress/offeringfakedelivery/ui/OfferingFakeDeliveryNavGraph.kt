package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.addOfferingFakeDeliveryDestination(
    onFakeOfferSent: () -> Unit,
    onUserMessage: (String) -> Unit
) {
    composable(route = "offering-fake-delivery") {
        val viewModel: OfferingFakeDeliveryViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        OfferingFakeDeliveryScreen(
            uiState,
            onSendFakeOffer = {
                viewModel.onNewEvent(
                    OfferingFakeDeliveryEvent.BroadcastFakeDeliveryRequested
                )
            }
        )
        uiState.userMessages.firstOrNull()?.let(onUserMessage)
        LaunchedEffect(key1 = uiState.isFakeOfferSent) {
            if (uiState.isFakeOfferSent) {
                onFakeOfferSent()
                viewModel.consumeIsFakeOfferSent()
            }
        }
    }
}

fun NavController.navigateToOfferingFakeDelivery() {
    navigate(route = "offering-fake-delivery")
}
