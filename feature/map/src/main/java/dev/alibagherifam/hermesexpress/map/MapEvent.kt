package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point

sealed interface MapEvent {
    object CameraMovedAccordingly : MapEvent
    object MarkersUpdated : MapEvent
    data class UserCoordinatesChange(val newCoordinates: Point) : MapEvent
}
