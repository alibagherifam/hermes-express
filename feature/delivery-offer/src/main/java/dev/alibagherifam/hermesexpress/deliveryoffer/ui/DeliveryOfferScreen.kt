package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
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
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        OfferEarningsText(uiState.offer.earnings)
        DeliveryConditionList(uiState.offer)
        TerminalList(
            terminals = uiState.offer.terminals,
            onTerminalClick,
            showInCompactMode = true
        )
        ProgressButton(
            progress = uiState.offerTimeElapsedPercentage,
            onPressStateChange = onAcceptOfferPressStateChange,
            Modifier.widthIn(min = 240.dp),
            isEnabled = !uiState.isAcceptingOfferInProgress,
            pressedStateColorSaturation = uiState.offerAcceptancePercentage
        ) {
            Text(text = stringResource(R.string.label_accept_delivery_offer))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
internal fun DeliveryOfferScreenPreview() {
    HermesTheme {
        val context = LocalContext.current
        DeliveryOfferScreen(
            uiState = DeliveryOfferUiState(
                offer = generateFakeDeliveryOffer { context.getString(it) }
            ),
            onAcceptOfferPressStateChange = {},
            onTerminalClick = {}
        )
    }
}
