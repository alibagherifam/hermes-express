package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.common.domain.formatCurrency
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.theme.HermesTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration

@Composable
fun DeliveryOfferScreen(
    uiState: DeliveryOfferUiState,
    onAcceptOfferClick: () -> Unit,
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
            onTerminalClick
        )
        Spacer(Modifier.size(16.dp))
        AcceptOfferButton(
            onClick = onAcceptOfferClick,
            Modifier.widthIn(min = 240.dp),
            isEnabled = !uiState.isAcceptingOfferInProgress
        )
    }
}

@Composable
fun AcceptOfferButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val expirationPercentage by rememberExpirationPercentage(
        expireDuration = with(Duration) { 20.seconds }
    )
    ProgressButton(
        onClick,
        progress = expirationPercentage,
        modifier,
        isEnabled
    ) {
        Text(text = "Accept Offer")
    }
}

@Composable
fun rememberExpirationPercentage(expireDuration: Duration): State<Float> {
    val percentage = remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = true) {
        var elapsedTime = Duration.ZERO
        val diff = with(Duration) { 50.milliseconds }
        while (elapsedTime < expireDuration) {
            elapsedTime += diff
            percentage.value = (elapsedTime / expireDuration).toFloat()
            delay(diff)
        }
    }
    return percentage
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun DeliveryOfferScreenPreview() {
    HermesTheme {
        DeliveryOfferScreen(
            uiState = DeliveryOfferUiState(
                offer = generateFakeDeliveryOffer()
            ),
            onAcceptOfferClick = {},
            onTerminalClick = {}
        )
    }
}
