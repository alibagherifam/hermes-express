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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.theme.AppTheme
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.formatCurrency
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

@Composable
fun DeliveryOfferScreen(
    offer: DeliveryOffer,
    onAcceptOfferClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatCurrency(offer.price),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.size(16.dp))
        TerminalList(
            terminals = offer.terminals,
            onTerminalClick = {})
        Spacer(Modifier.size(16.dp))
        AcceptOfferButton(
            onClick = onAcceptOfferClick,
            Modifier.widthIn(min = 240.dp)
        )
    }
}

@Composable
fun AcceptOfferButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val expirationPercentage by rememberExpirationPercentage(
        expireDuration = with(Duration) { 20.seconds }
    )
    ProgressButton(
        onClick,
        progress = expirationPercentage,
        modifier
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
        launch {
            while (elapsedTime < expireDuration) {
                elapsedTime += diff
                percentage.value = (elapsedTime / expireDuration).toFloat()
                delay(diff)
            }
        }
    }
    return percentage
}

@Preview(showBackground = true)
@Composable
fun DeliveryOfferScreenPreview() {
    AppTheme {
        DeliveryOfferScreen(
            offer = generateFakeDeliveryOffer(),
            onAcceptOfferClick = {}
        )
    }
}
