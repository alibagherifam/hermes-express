package dev.alibagherifam.hermesexpress.map.api

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.ColorInt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions

data class MarkerOptions(
    val size: Float,
    val cornerRadius: Float,
    val textSize: TextUnit,
    @ColorInt val textColor: Int,
    @ColorInt val containerColor: Int
)

internal fun MarkerOptions.toPointAnnotationOptions(): PointAnnotationOptions =
    PointAnnotationOptions()
        .withTextColor(this.textColor)
        .withTextSize(this.textSize.value.toDouble())
        .withIconImage(drawRoundedCornerSquareBitmap(this))

private fun drawRoundedCornerSquareBitmap(markerOptions: MarkerOptions): Bitmap {
    val bitmapSize = markerOptions.size.toInt()
    val bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val paint = Paint().apply {
        color = markerOptions.containerColor
        style = Paint.Style.FILL
    }

    val topLeftCoordinate = 0f
    val bottomRightCoordinate = topLeftCoordinate + markerOptions.size
    val rect = RectF(
        topLeftCoordinate,
        topLeftCoordinate,
        bottomRightCoordinate,
        bottomRightCoordinate
    )

    val cornerRadius = markerOptions.cornerRadius
    canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
    return bitmap
}

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
