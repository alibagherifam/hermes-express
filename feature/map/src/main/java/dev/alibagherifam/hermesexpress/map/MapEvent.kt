package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point

internal sealed interface MapEvent {
    object CameraMovedAccordingly : MapEvent
    object CameraFittedForMarkers : MapEvent
    data class UserCoordinatesChange(val newCoordinates: Point) : MapEvent
}
