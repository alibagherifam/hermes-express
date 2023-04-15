package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.playNotificationSound
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.addDeliveryOfferDestination(
    onOfferAccepted: () -> Unit,
    onOfferExpired: () -> Unit,
    onTerminalClick: (Terminal) -> Unit
) {
    composable(route = "delivery-offer") {
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            playNotificationSound(context, soundResId = R.raw.sfx_harp)
        }
        val viewModel: DeliveryOfferViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        DeliveryOfferScreen(
            uiState,
            onAcceptOfferClick = viewModel::acceptOffer,
            onTerminalClick
        )
        if (uiState.isOfferAccepted || uiState.isOfferExpired) {
            SideEffect {
                when {
                    uiState.isOfferAccepted -> onOfferAccepted()
                    uiState.isOfferExpired -> onOfferExpired()
                }
                viewModel.resetState()
            }
        }
    }
}

fun NavHostController.navigateToDeliveryOffer() {
    navigate(route = "delivery-offer")
}
