package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
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

internal fun LocationComponentPlugin.getUserLocationStream(): Flow<Point> = callbackFlow {
    val listener = OnIndicatorPositionChangedListener {
        trySendBlocking(it)
    }
    addOnIndicatorPositionChangedListener(listener)
    awaitClose {
        removeOnIndicatorPositionChangedListener(listener)
    }
}.conflate()

internal fun MapboxMap.fitCameraForLocations(locations: List<Point>) {
    val defaultPadding = 100.0
    val markerPaddingValues = EdgeInsets(
        defaultPadding,
        defaultPadding,
        defaultPadding * 10,
        defaultPadding
    )
    val fittedViewPort = cameraForCoordinates(locations, markerPaddingValues)
    easeTo(fittedViewPort)
}
