package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeliveryOfferDestination(
    onOfferAccepted: () -> Unit,
    onTerminalClick: (Terminal) -> Unit,
    viewModel: DeliveryOfferViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    DeliveryOfferScreen(
        uiState,
        onAcceptOfferClick = viewModel::acceptOffer,
        onTerminalClick
    )
    if (uiState.isOfferAccepted) {
        SideEffect { onOfferAccepted() }
    }
}
