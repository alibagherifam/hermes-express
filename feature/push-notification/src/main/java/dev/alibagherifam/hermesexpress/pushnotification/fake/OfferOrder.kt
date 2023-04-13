package dev.alibagherifam.hermesexpress.pushnotification.fake

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.theme.AppTheme
import dev.alibagherifam.hermesexpress.pushnotification.R

@Composable
fun OfferOrder(
    onOfferOrderClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onOfferOrderClick) {
            Text(text = stringResource(R.string.label_offer_order))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OfferOrderPreview() {
    AppTheme {
        OfferOrder(
            onOfferOrderClick = {}
        )
    }
}
