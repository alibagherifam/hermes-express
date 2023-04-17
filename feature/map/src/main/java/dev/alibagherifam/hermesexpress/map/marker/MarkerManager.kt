package dev.alibagherifam.hermesexpress.map.marker

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions

internal class MarkerManager(private val markerColors: MarkerColors) {
    var pointAnnotationManager: PointAnnotationManager? = null

    fun addMarkers(coordinates: List<Point>) {
        checkNotNull(pointAnnotationManager).addMarkers(coordinates)
    }

    fun deleteAllMarkers() {
        checkNotNull(pointAnnotationManager).deleteAll()
    }

    private fun PointAnnotationManager.addMarkers(coordinates: List<Point>) {
        val markerOptions = PointAnnotationOptions()
            .withTextColor(markerColors.textColor)
            .withTextSize(14.0)
            .withIconImage(drawCircleBitmap())

        for (index in coordinates.indices) {
            markerOptions
                .withPoint(coordinates[index])
                .withTextField((index + 1).toString())
            create(markerOptions)
        }
    }

    private fun drawCircleBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        val centerX = canvas.width / 2f
        val centerY = canvas.height / 2f

        val strokeWidth = 4f
        val radiusOuter = canvas.width / 2f
        paint.color = markerColors.strokeColor
        canvas.drawCircle(centerX, centerY, radiusOuter, paint)

        val radiusInner = radiusOuter - strokeWidth
        paint.color = markerColors.containerColor
        canvas.drawCircle(centerX, centerY, radiusInner, paint)

        return bitmap
    }
}
