package dev.alibagherifam.hermesexpress.map.screen

import com.mapbox.geojson.Point

internal data class MapState(
    var userCoordinates: Point? = null,
    val requestedCameraLatLong: Point? = null,
    val isAnyMarkerUpdateAvailable: Boolean = false,
    val markerCoordinates: List<Point> = emptyList()
)
