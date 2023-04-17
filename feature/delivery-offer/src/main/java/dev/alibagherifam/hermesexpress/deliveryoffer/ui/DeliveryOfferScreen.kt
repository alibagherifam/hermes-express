package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.common.domain.formatCurrency
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatCurrency(uiState.offer.price),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.size(16.dp))
        TerminalList(
            terminals = uiState.offer.terminals,
            onTerminalClick,
            showInCompactMode = true
        )
        Spacer(Modifier.size(16.dp))
        ProgressButton(
            progress = uiState.offerTimeElapsedPercentage,
            onPressStateChange = onAcceptOfferPressStateChange,
            Modifier.widthIn(min = 240.dp),
            isEnabled = !uiState.isAcceptingOfferInProgress,
            contentColorAlpha = uiState.offerAcceptancePercentage
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
