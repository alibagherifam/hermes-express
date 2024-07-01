package dev.alibagherifam.hermesexpress.map.screen

import com.mapbox.geojson.Point

internal sealed interface MapEvent {
    object CameraMovedAccordingly : MapEvent
    object MarkersUpdated : MapEvent
    data class UserLocationChange(val newLocation: Point) : MapEvent
}
