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

    fun addMarkers(coordinates: List<Point>) {
        checkNotNull(pointAnnotationManager).addMarkers(coordinates)
    }

    fun deleteAllMarkers() {
        checkNotNull(pointAnnotationManager).deleteAll()
    }

    private fun PointAnnotationManager.addMarkers(coordinates: List<Point>) {
        val markerOptions = PointAnnotationOptions()
            .withTextColor(markerOptions.textColor)
            .withTextSize(markerOptions.textSize.toDouble())
            .withIconImage(drawCircleBitmap())

        for (index in coordinates.indices) {
            markerOptions
                .withPoint(coordinates[index])
                .withTextField((index + 1).toString())
            create(markerOptions)
        }
    }

    private fun drawCircleBitmap(): Bitmap {
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
