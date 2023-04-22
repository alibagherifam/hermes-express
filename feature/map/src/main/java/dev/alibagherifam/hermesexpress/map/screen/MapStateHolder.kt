package dev.alibagherifam.hermesexpress.map.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dev.alibagherifam.hermesexpress.common.domain.LatLong
import dev.alibagherifam.hermesexpress.map.toPoint

class MapStateHolder {
    private val _state = mutableStateOf(MapState())
    internal val state: State<MapState> get() = _state

    fun moveCamera(targetLocation: LatLong) {
        updateState {
            it.copy(requestedCameraLocation = targetLocation.toPoint())
        }
    }

    internal fun moveCameraToUserLocation() {
        updateState {
            it.copy(requestedCameraLocation = it.userLocation)
        }
    }

    fun setMarkerLocations(locations: List<LatLong>) {
        val markerPoints = locations.map { it.toPoint() }
        if (markerPoints != state.value.markerLocations) {
            updateState { oldState ->
                oldState.copy(
                    markerLocations = markerPoints,
                    isAnyMarkerUpdateAvailable = true
                )
            }
        }
    }

    internal fun onNewEvent(event: MapEvent) {
        when (event) {
            MapEvent.MarkersUpdated -> updateState {
                it.copy(isAnyMarkerUpdateAvailable = false)
            }

            MapEvent.CameraMovedAccordingly -> updateState {
                it.copy(requestedCameraLocation = null)
            }

            is MapEvent.UserLocationChange -> {
                if (state.value.userLocation == null) {
                    updateState {
                        it.copy(
                            requestedCameraLocation = event.newLocation,
                            userLocation = event.newLocation
                        )
                    }
                } else {
                    _state.value.userLocation = event.newLocation
                }
            }
        }
    }

    private fun updateState(update: (MapState) -> MapState) {
        _state.value = update(_state.value)
    }
}
