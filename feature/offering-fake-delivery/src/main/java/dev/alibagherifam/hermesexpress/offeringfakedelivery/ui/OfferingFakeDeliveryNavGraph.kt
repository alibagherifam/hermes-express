package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun OfferingFakeDeliveryDestination(
    onFakeOfferSent: () -> Unit,
    viewModel: OfferingFakeDeliveryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    OfferingFakeDeliveryScreen(
        uiState,
        onSendFakeOffer = viewModel::broadcastFakeDeliveryOffer
    )
    if (uiState.isFakeOfferSent) {
        SideEffect { onFakeOfferSent() }
    }
}
