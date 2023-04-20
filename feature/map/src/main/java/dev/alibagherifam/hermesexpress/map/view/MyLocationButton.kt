package dev.alibagherifam.hermesexpress.map.view

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.feature.map.R
import kotlin.math.roundToInt

@Composable
internal fun MyLocationButton(
    onClick: () -> Unit,
    bottomSheetOffset: Float,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick,
        modifier.offset {
            val spaceBetween = 70.dp.toPx()
            IntOffset(x = 0, y = (bottomSheetOffset - spaceBetween).roundToInt())
        },
        shape = CircleShape,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_locate_me),
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
            bottomSheetOffset = with(LocalDensity.current) { 70.dp.toPx() }
        )
    }
}
