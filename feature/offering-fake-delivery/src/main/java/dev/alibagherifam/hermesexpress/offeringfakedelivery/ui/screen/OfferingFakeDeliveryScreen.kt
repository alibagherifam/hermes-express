package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.ui.LocalizationPreviews
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.offeringfakedelivery.R

@Composable
internal fun OfferingFakeDeliveryScreen(
    uiState: OfferingFakeDeliveryUiState,
    onSendFakeOffer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onSendFakeOffer,
            enabled = !uiState.isOfferingInProgress
        ) {
            Text(text = stringResource(R.string.label_offer_fake_delivery))
        }
        if (uiState.isOfferingInProgress) {
            CircularProgressIndicator()
        }
    }
}

@LocalizationPreviews
@Composable
internal fun OfferingFakeDeliveryScreenPreview() {
    HermesTheme {
        OfferingFakeDeliveryScreen(
            uiState = OfferingFakeDeliveryUiState(),
            onSendFakeOffer = {}
        )
    }
}
