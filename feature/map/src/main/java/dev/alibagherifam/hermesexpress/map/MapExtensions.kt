package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

typealias LatLong = Pair<Double, Double>

fun LatLong.toPoint(): Point {
    val (latitude, longitude) = this
    return Point.fromLngLat(longitude, latitude)
}

fun CircleAnnotationManager.addMarkers(coordinates: List<Point>) {
    val markerOptions = CircleAnnotationOptions()
        .withCircleRadius(circleRadius = 8.0)
        .withCircleColor(circleColor = "#EE4E8b")
        .withCircleStrokeWidth(circleStrokeWidth = 2.0)
        .withCircleStrokeColor(circleStrokeColor = "#FFFFFF")

    for (point in coordinates) {
        markerOptions.withPoint(point)
        create(markerOptions)
    }
}

fun MapView.locationFlow(): Flow<Point> = callbackFlow {
    val positionChangedListener = OnIndicatorPositionChangedListener { point ->
        trySendBlocking(point)
    }
    location.addOnIndicatorPositionChangedListener(positionChangedListener)
    awaitClose { location.removeOnIndicatorPositionChangedListener(positionChangedListener) }
}.conflate()

fun MapView.zoomCameraOnCoordinate(
    coordinates: Point,
    zoomLevel: Double = 14.0
) {
    val cameraController = getMapboxMap()
    val cameraOptions = CameraOptions.Builder()
        .center(coordinates)
        .zoom(zoomLevel)
        .build()
    cameraController.easeTo(cameraOptions)
}

fun MapView.fitCameraForCoordinates(coordinates: List<Point>) {
    val cameraController = getMapboxMap()
    val viewportPadding = EdgeInsets(100.0, 100.0, 500.0, 100.0)
    cameraController.run {
        val fittedViewPort = cameraForCoordinates(coordinates, viewportPadding)
        easeTo(fittedViewPort)
    }
}
