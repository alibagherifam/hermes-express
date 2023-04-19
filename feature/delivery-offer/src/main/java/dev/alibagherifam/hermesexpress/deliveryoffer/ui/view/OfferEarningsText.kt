package dev.alibagherifam.hermesexpress.deliveryoffer.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.formatCurrency
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R

@Composable
internal fun OfferEarningsText(
    earnings: Float,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Spacer(Modifier.weight(0.5f))
        Text(
            text = formatCurrency(earnings),
            Modifier.alignByBaseline(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(R.string.label_currency_unit),
            Modifier
                .weight(0.5f)
                .padding(start = 4.dp)
                .alignByBaseline(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
