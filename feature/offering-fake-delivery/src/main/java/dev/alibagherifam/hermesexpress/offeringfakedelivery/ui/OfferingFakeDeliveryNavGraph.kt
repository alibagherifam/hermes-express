package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.addOfferingFakeDeliveryDestination(
    onFakeOfferSent: () -> Unit,
) {
    composable(route = "offering-fake-delivery") {
        val viewModel: OfferingFakeDeliveryViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        OfferingFakeDeliveryScreen(
            uiState,
            onSendFakeOffer = viewModel::broadcastFakeDeliveryOffer
        )
        if (uiState.isFakeOfferSent) {
            SideEffect {
                onFakeOfferSent()
                viewModel.resetState()
            }
        }
    }
}

fun NavController.navigateToOfferingFakeDelivery() {
    navigate(route = "offering-fake-delivery")
}
