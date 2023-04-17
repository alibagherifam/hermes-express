package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point

data class MapState(
    var userCoordinates: Point? = null,
    val requestedCameraLatLong: Point? = null,
    val isAnyMarkerUpdateAvailable: Boolean = false,
    val markerCoordinates: List<Point> = emptyList()
)
