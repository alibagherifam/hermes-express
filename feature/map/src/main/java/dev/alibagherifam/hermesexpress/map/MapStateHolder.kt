package dev.alibagherifam.hermesexpress.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.geojson.Point

class MapStateHolder {
    internal var userLatLong: Point? = null
    internal var shouldFitCameraForMarkers: Boolean = false

    internal var fixedCameraLatLong by mutableStateOf<Point?>(null)
    internal var markerLatLongs by mutableStateOf<List<Point>>(emptyList())

    fun moveCamera(to: LatLong) {
        fixedCameraLatLong = to.toPoint()
    }

    fun moveCameraToUserCoordinates() {
        userLatLong?.let { fixedCameraLatLong = it }
    }

    fun updateMarkerCoordinates(coordinates: List<LatLong>) {
        markerLatLongs = coordinates.map { it.toPoint() }
        shouldFitCameraForMarkers = (userLatLong != null)
    }

    internal fun onNewEvent(event: MapEvent) {
        when (event) {
            MapEvent.CameraFittedForMarkers -> {
                shouldFitCameraForMarkers = false
            }

            MapEvent.CameraMovedAccordingly -> {
                fixedCameraLatLong = null
            }

            is MapEvent.UserCoordinatesChange -> {
                if (userLatLong == null) {
                    fixedCameraLatLong = event.newCoordinates
                }
                userLatLong = event.newCoordinates
            }
        }
    }
}
