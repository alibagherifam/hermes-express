package dev.alibagherifam.hermesexpress.map.screen

import com.mapbox.geojson.Point

internal sealed interface MapEvent {
    object CameraMovedAccordingly : MapEvent
    object MarkersUpdated : MapEvent
    data class UserCoordinatesChange(val newCoordinates: Point) : MapEvent
}