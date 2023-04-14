package dev.alibagherifam.hermesexpress.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.mapbox.geojson.Point

class MapState {
    val userCoordinate: MutableState<Point?> = mutableStateOf(null)
    val markerCoordinates: MutableState<List<Point>> = mutableStateOf(emptyList())

    fun updateMarkerCoordinates(coordinates: List<Pair<Double, Double>>) {
        markerCoordinates.value = coordinates.map { (latitude, longitude) ->
            Point.fromLngLat(longitude, latitude)
        }
    }
}
