package dev.alibagherifam.hermesexpress.map.marker

import androidx.annotation.ColorInt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb

data class MarkerColors(
    @ColorInt val textColor: Int,
    @ColorInt val containerColor: Int,
    @ColorInt val strokeColor: Int
)

@Composable
fun markerDefaultColors() = with(MaterialTheme.colorScheme) {
    MarkerColors(
        textColor = onSurface.toArgb(),
        containerColor = surface.toArgb(),
        strokeColor = primary.toArgb()
    )
}
