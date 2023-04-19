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
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.playAudio
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.vibrateDevice
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration

fun NavGraphBuilder.addDeliveryOfferDestination(
    onOfferAccepted: () -> Unit,
    onOfferExpired: () -> Unit,
    onTerminalClick: (Terminal) -> Unit,
    onUserMessage: (String) -> Unit
) {
    composable(route = "delivery-offer") {
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            playAudio(context, audioResId = R.raw.sfx_harp)
        }
        val viewModel: DeliveryOfferViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        DeliveryOfferScreen(
            uiState,
            onAcceptOfferPressStateChange = { isPressed ->
                viewModel.onNewEvent(
                    DeliveryOfferEvent.AcceptOfferPressStateChange(isPressed)
                )
            },
            onTerminalClick
        )
        uiState.userMessages.firstOrNull()?.let(onUserMessage)
        if (uiState.isOfferAccepted || uiState.isOfferExpired) {
            SideEffect {
                when {
                    uiState.isOfferAccepted -> {
                        vibrateDevice(context, with(Duration) { 800.milliseconds })
                        onOfferAccepted()
                    }

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
