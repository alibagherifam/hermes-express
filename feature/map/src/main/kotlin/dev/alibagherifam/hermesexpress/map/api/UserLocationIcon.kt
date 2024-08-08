package dev.alibagherifam.hermesexpress.map.api

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.roundToInt
import dev.alibagherifam.hermesexpress.common.R as CommonR

data class UserLocationIcon(
    val foreground: Bitmap?,
    val background: Bitmap?
)

internal fun UserLocationIcon.isEmpty(): Boolean =
    (foreground == null) && (background == null)

@Composable
fun userLocationDefaultIcon(): UserLocationIcon {
    val density = LocalDensity.current
    val backgroundColor = MaterialTheme.colorScheme.inversePrimary
    val contentColor = contentColorFor(backgroundColor)
    val context = LocalContext.current

    return UserLocationIcon(
        foreground = ContextCompat
            .getDrawable(context, CommonR.drawable.logo)
            ?.apply { setTint(contentColor.toArgb()) }
            ?.toBitmap(),
        background = createCircleBitmapWithStroke(
            size = with(density) { 32.dp.toPx() },
            fillColor = backgroundColor.toArgb(),
            strokeSize = with(density) { (1.5).dp.toPx() },
            strokeColor = contentColor.toArgb()
        )
    )
}

internal fun createCircleBitmapWithStroke(
    size: Float,
    fillColor: Int,
    strokeSize: Float,
    strokeColor: Int
): Bitmap {
    val bitmap = Bitmap.createBitmap(
        size.roundToInt(),
        size.roundToInt(),
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    val center = size / 2f

    val paint = Paint().apply {
        color = fillColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    val innerRadius = (size / 2f) - strokeSize
    canvas.drawCircle(center, center, innerRadius, paint)

    paint.apply {
        color = strokeColor
        style = Paint.Style.STROKE
        strokeWidth = strokeSize
    }
    val outerRadius = innerRadius + (strokeSize / 2f)
    canvas.drawCircle(center, center, outerRadius, paint)

    return bitmap
}
