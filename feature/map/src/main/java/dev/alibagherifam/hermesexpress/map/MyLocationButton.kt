package dev.alibagherifam.hermesexpress.map

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.feature.map.R

@Composable
internal fun MyLocationButton(
    onClick: () -> Unit,
    bottomSheetOffset: Float,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick,
        modifier.offset(
            y = with(LocalDensity.current) {
                bottomSheetOffset.toDp() - 70.dp
            }
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_my_location),
            contentDescription = stringResource(R.string.a11y_my_location_button)
        )
    }
}

@Preview
@Composable
internal fun MyLocationButtonPreview() {
    HermesTheme {
        MyLocationButton(
            onClick = {},
            bottomSheetOffset = 0f
        )
    }
}
