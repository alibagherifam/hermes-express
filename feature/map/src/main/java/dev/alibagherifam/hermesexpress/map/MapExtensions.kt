package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import dev.alibagherifam.hermesexpress.common.domain.LatLong
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

internal fun LatLong.toPoint(): Point {
    val (latitude, longitude) = this
    return Point.fromLngLat(longitude, latitude)
}

internal fun MapView.getUserLocationStream(): Flow<Point> = callbackFlow {
    val listener = OnIndicatorPositionChangedListener { point ->
        trySendBlocking(point)
    }
    location.addOnIndicatorPositionChangedListener(listener)
    awaitClose {
        location.removeOnIndicatorPositionChangedListener(listener)
    }
}.conflate()

internal fun MapboxMap.fitCameraForLocations(locations: List<Point>) {
    val viewportPadding = EdgeInsets(0.0, 100.0, 1000.0, 100.0)
    val fittedViewPort = cameraForCoordinates(locations, viewportPadding)
    easeTo(fittedViewPort)
}
