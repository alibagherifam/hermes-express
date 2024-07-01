package dev.alibagherifam.hermesexpress.map.marker

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions

internal class MarkerManager(private val markerOptions: MarkerOptions) {
    var pointAnnotationManager: PointAnnotationManager? = null

    fun addMarkers(locations: List<Point>) {
        checkNotNull(pointAnnotationManager).addMarkers(locations)
    }

    fun deleteAllMarkers() {
        checkNotNull(pointAnnotationManager).deleteAll()
    }

    private fun PointAnnotationManager.addMarkers(locations: List<Point>) {
        val markerOptions = PointAnnotationOptions()
            .withTextColor(markerOptions.textColor)
            .withTextSize(markerOptions.textSize.value.toDouble())
            .withIconImage(drawRoundedCornerSquareBitmap())

        for (index in locations.indices) {
            markerOptions
                .withPoint(locations[index])
                .withTextField((index + 1).toString())
            create(markerOptions)
        }
    }

    private fun drawRoundedCornerSquareBitmap(): Bitmap {
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
}
