package dev.alibagherifam.hermesexpress.map.screen

import com.mapbox.geojson.Point

internal data class MapState(
    var userLocation: Point? = null,
    val requestedCameraLocation: Point? = null,
    val isAnyMarkerUpdateAvailable: Boolean = false,
    val markerLocations: List<Point> = emptyList()
)
