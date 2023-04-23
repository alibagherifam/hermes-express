package dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.ui.LocalizationPreviews
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.FormatCurrencyUseCase
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.view.DeliveryConditionList
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.view.OfferEarningsText
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.view.ProgressButton
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.view.TerminalList
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R

@Composable
internal fun DeliveryOfferScreen(
    uiState: DeliveryOfferUiState,
    onAcceptOfferPressStateChange: (Boolean) -> Unit,
    onTerminalClick: (Terminal) -> Unit,
    modifier: Modifier = Modifier
) {
    uiState.offer ?: return
    Column(
        modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OfferEarningsText(uiState.offer.earnings)
        Spacer(Modifier.size(6.dp))
        DeliveryConditionList(uiState.offer)
        TerminalList(
            terminals = uiState.offer.terminals,
            onTerminalClick,
            showInCompactMode = true
        )
        Box(contentAlignment = Alignment.Center) {
            ProgressButton(
                progress = uiState.offerTimeElapsedPercentage,
                onPressStateChange = onAcceptOfferPressStateChange,
                modifier = Modifier.widthIn(min = 260.dp),
                isEnabled = !uiState.isAcceptingOfferInProgress,
                pressedStateColorSaturation = uiState.offerAcceptanceConfirmationPercentage
            ) {
                Text(text = stringResource(R.string.label_accept_delivery_offer))
            }
            if (uiState.isAcceptingOfferInProgress) {
                CircularProgressIndicator()
            }
        }
    }
}

@LocalizationPreviews
@Composable
internal fun DeliveryOfferScreenPreview() {
    HermesTheme {
        val stringProvider = with(LocalContext.current) {
            StringProvider { getString(it) }
        }
        val offer = generateFakeDeliveryOffer(stringProvider)
            .toUiModel(FormatCurrencyUseCase())

        DeliveryOfferScreen(
            uiState = DeliveryOfferUiState(offer),
            onAcceptOfferPressStateChange = {},
            onTerminalClick = {}
        )
    }
}
