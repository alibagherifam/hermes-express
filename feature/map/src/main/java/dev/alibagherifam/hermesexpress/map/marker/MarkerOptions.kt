package dev.alibagherifam.hermesexpress.map.marker

import androidx.annotation.ColorInt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

data class MarkerOptions(
    val size: Float,
    val cornerRadius: Float,
    val textSize: Float,
    @ColorInt val textColor: Int,
    @ColorInt val containerColor: Int
)

@Composable
fun markerDefaultOptions(): MarkerOptions {
    return with(MaterialTheme.colorScheme) {
        with(LocalDensity.current) {
            MarkerOptions(
                size = 20.dp.toPx(),
                cornerRadius = 6.dp.toPx(),
                textSize = 16f,
                textColor = onError.toArgb(),
                containerColor = onErrorContainer.toArgb()
            )
        }
    }
}
