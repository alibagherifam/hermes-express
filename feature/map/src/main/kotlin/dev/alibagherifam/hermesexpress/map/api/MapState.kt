package dev.alibagherifam.hermesexpress.map.api

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dev.alibagherifam.hermesexpress.common.domain.Location

abstract class MapState {
    private val _userLocation: MutableState<Location?> = mutableStateOf(null)
    val userLocation: State<Location?> get() = _userLocation

    fun updateUserLocation(newLocation: Location?) {
        _userLocation.value = newLocation
    }

    private val _markerLocations: MutableState<List<Location>> = mutableStateOf(listOf())
    val markerLocations: State<List<Location>> get() = _markerLocations

    fun updateMarkerLocations(locations: List<Location>) {
        _markerLocations.value = locations
    }

    abstract suspend fun animateCamera(cameraSettings: CameraSettings)

    abstract suspend fun boundCameraAnimated(locations: List<Location>)

    abstract fun boundCamera(locations: List<Location>)
}

fun MapState.boundCameraToUserLocationAndMarkers() {
    val allLocations = getUserAndMarkerLocations()
    if (allLocations.isNotEmpty()) {
        boundCamera(allLocations)
    }
}

suspend fun MapState.boundCameraToUserLocationAndMarkersAnimated() {
    val allLocations = getUserAndMarkerLocations()
    if (allLocations.isNotEmpty()) {
        boundCameraAnimated(allLocations)
    }
}

private fun MapState.getUserAndMarkerLocations(): List<Location> =
    getUserLocationAsList() + markerLocations.value

private fun MapState.getUserLocationAsList(): List<Location> =
    listOfNotNull(userLocation.value)

suspend fun MapState.focusCameraOnUserLocationAnimated() {
    userLocation.value?.let {
        focusCameraOnLocationAnimated(it)
    }
}

suspend fun MapState.focusCameraOnLocationAnimated(location: Location) {
    val cameraSettings = CameraSettings(
        location = location,
        zoom = ZoomLevel.NORMAL
    )
    animateCamera(cameraSettings)
}
