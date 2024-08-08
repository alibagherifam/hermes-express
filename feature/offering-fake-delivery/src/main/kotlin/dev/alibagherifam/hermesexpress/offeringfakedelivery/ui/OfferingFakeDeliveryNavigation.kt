package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen.OfferingFakeDeliveryEvent
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen.OfferingFakeDeliveryScreen
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen.OfferingFakeDeliveryViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object OfferingFakeDelivery

fun NavGraphBuilder.offeringFakeDeliveryScreen(
    onFakeOfferSent: () -> Unit,
    onUserMessage: (String) -> Unit
) {
    composable<OfferingFakeDelivery> {
        val viewModel: OfferingFakeDeliveryViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        OfferingFakeDeliveryScreen(
            uiState = uiState,
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
