package dev.alibagherifam.hermesexpress.deliveryoffer.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DashedVerticalDivider(
    modifier: Modifier = Modifier,
    width: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.scrim,
    drawHalfTop: Boolean = true,
    drawHalfBottom: Boolean = true
) {
    Canvas(modifier.width(width)) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)
        val halfWidth = size.width / 2
        val halfHeight = size.height / 2
        val startY = if (drawHalfTop) 0f else halfHeight
        val endY = if (drawHalfBottom) size.height else halfHeight
        drawLine(
            color = color,
            start = Offset(halfWidth, startY),
            strokeWidth = size.width,
            end = Offset(halfWidth, endY),
            pathEffect = pathEffect
        )
    }
}
