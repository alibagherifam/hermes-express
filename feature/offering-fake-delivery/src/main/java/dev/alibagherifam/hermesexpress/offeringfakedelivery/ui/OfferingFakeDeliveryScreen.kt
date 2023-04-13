package dev.alibagherifam.hermesexpress.offeringfakedelivery.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.theme.AppTheme
import dev.alibagherifam.hermesexpress.offeringfakedelivery.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun OfferingFakeDeliveryScreen(
    viewModel: OfferingFakeDeliveryViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = viewModel::broadcastFakeDeliveryOffer,
            enabled = !state.isOfferingInProgress
        ) {
            Text(text = stringResource(R.string.label_offer_fake_delivery))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OfferingFakeDeliveryScreenPreview() {
    AppTheme {
        OfferingFakeDeliveryScreen(
            viewModel = koinViewModel()
        )
    }
}