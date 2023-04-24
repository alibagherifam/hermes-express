package dev.alibagherifam.hermesexpress.map.screen

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions

internal data class MapState(
    var userLocation: Point? = null,
    val requestedCameraOptions: CameraOptions? = null,
    val isAnyMarkerUpdateAvailable: Boolean = false,
    val markerLocations: List<Point> = emptyList()
) {
    val shouldUpdateMarkers: Boolean
        get() = isAnyMarkerUpdateAvailable
                && userLocation != null
                && requestedCameraOptions == null
}
