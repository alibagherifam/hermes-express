package dev.alibagherifam.hermesexpress.map.marker

import androidx.annotation.ColorInt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

data class MarkerOptions(
    val size: Float,
    val cornerRadius: Float,
    val textSize: TextUnit,
    @ColorInt val textColor: Int,
    @ColorInt val containerColor: Int
)

@Composable
fun markerDefaultOptions(
    size: Dp = 20.dp,
    cornerRadius: Dp = 6.dp,
    textSize: TextUnit = MaterialTheme.typography.labelLarge.fontSize,
    textColor: Color = MaterialTheme.colorScheme.onError,
    containerColor: Color = MaterialTheme.colorScheme.onErrorContainer
): MarkerOptions = with(LocalDensity.current) {
    MarkerOptions(
        size = size.toPx(),
        cornerRadius = cornerRadius.toPx(),
        textSize = textSize,
        textColor = textColor.toArgb(),
        containerColor = containerColor.toArgb()
    )
}
