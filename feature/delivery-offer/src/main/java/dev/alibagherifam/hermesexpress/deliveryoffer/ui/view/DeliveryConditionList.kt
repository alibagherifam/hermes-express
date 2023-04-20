package dev.alibagherifam.hermesexpress.deliveryoffer.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.generateFakeDeliveryOffer
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.feature.deliveryoffer.R

@Composable
internal fun DeliveryConditionList(offer: DeliveryOffer) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        DeliveryConditionItem(
            text = stringResource(
                R.string.label_minutes,
                offer.estimatedDeliveryTime.inWholeMinutes
            ),
            contentDescription = stringResource(R.string.a11y_estimated_delivery_time),
            iconResId = R.drawable.ic_timer_outline,
            iconSize = 18.dp
        )
        if (offer.isShipmentFragile) {
            DeliveryConditionItem(
                text = stringResource(R.string.label_fragile),
                contentDescription = stringResource(R.string.a11y_fragile_shipment),
                iconResId = R.drawable.ic_fragile_filled,
                iconSize = 20.dp
            )
        }
        if (offer.isBoxRequired) {
            DeliveryConditionItem(
                text = stringResource(R.string.label_box_required),
                contentDescription = stringResource(R.string.a11y_box_required),
                iconResId = R.drawable.ic_box_outline,
                iconSize = 30.dp
            )
        }
    }
}

@Composable
internal fun DeliveryConditionItem(
    text: String,
    contentDescription: String,
    @DrawableRes iconResId: Int,
    iconSize: Dp
) {
    SuggestionChip(
        onClick = { },
        label = { Text(text) },
        icon = {
            Icon(
                painterResource(iconResId),
                contentDescription,
                Modifier.size(iconSize)
            )
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
internal fun DeliveryConditionListPreview() {
    HermesTheme {
        val context = LocalContext.current
        DeliveryConditionList(
            offer = generateFakeDeliveryOffer { context.getString(it) }
        )
    }
}
