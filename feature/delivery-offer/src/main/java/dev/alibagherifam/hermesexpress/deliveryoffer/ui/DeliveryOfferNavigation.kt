package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.deliveryoffer.playAudio
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen.DeliveryOfferEvent
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen.DeliveryOfferScreen
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen.DeliveryOfferViewModel
import dev.alibagherifam.hermesexpress.deliveryoffer.vibrateDevice
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration

const val deliveryOfferRoute = "delivery-offer"

fun NavGraphBuilder.addDeliveryOfferScreen(
    onOfferAccepted: () -> Unit,
    onOfferExpired: () -> Unit,
    onTerminalClick: (Terminal) -> Unit,
    onUserMessage: (String) -> Unit
) {
    composable(deliveryOfferRoute) {
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            playAudio(context, audioResId = R.raw.sfx_harp)
        }
        val viewModel: DeliveryOfferViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        DeliveryOfferScreen(
            uiState,
            onAcceptOfferPressStateChange = { isPressed ->
                viewModel.onNewEvent(
                    DeliveryOfferEvent.AcceptOfferPressStateChange(isPressed)
                )
            },
            onTerminalClick
        )
        uiState.userMessages.firstOrNull()?.let { message ->
            LaunchedEffect(key1 = message) {
                onUserMessage(message)
                viewModel.onNewEvent(
                    DeliveryOfferEvent.UserMessageShown(message)
                )
            }
        }
        val isNavigationRequired = uiState.isOfferAccepted || uiState.isOfferExpired
        if (isNavigationRequired) {
            LaunchedEffect(key1 = Unit) {
                when {
                    uiState.isOfferAccepted -> {
                        vibrateDevice(context, with(Duration) { 800.milliseconds })
                        onOfferAccepted()
                    }

                    else -> {
                        onOfferExpired()
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateToDeliveryOffer() {
    navigate(deliveryOfferRoute)
}
