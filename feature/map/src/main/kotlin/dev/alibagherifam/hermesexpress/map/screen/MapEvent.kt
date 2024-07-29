package dev.alibagherifam.hermesexpress.map.screen

import com.mapbox.geojson.Point

internal sealed interface MapEvent {
    data object CameraMovedAccordingly : MapEvent
    data object MarkersUpdated : MapEvent
    data class UserLocationChange(val newLocation: Point) : MapEvent
}
