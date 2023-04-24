package dev.alibagherifam.hermesexpress.map.marker

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt
import dev.alibagherifam.hermesexpress.common.R as CommonR

class UserLocationIcon(val foreground: Drawable?, val background: Drawable?)

@Composable
fun userLocationDefaultIcon(): UserLocationIcon {
    val context = LocalContext.current
    val density = LocalDensity.current
    val backgroundColor = MaterialTheme.colorScheme.inversePrimary
    val contentColor = contentColorFor(backgroundColor)

    val bitmap = createCircleBitmapWithStroke(
        size = with(density) { 32.dp.toPx() },
        fillColor = backgroundColor.toArgb(),
        strokeSize = with(density) { (1.5).dp.toPx() },
        strokeColor = contentColor.toArgb()
    )
    val background = BitmapDrawable(context.resources, bitmap)

    val foreground = ContextCompat
        .getDrawable(context, CommonR.drawable.logo)
        ?.apply { setTint(contentColor.toArgb()) }

    return UserLocationIcon(foreground, background)
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
