package dev.alibagherifam.hermesexpress.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class MapStateHolder {
    private val _state = mutableStateOf(MapState())
    val state: State<MapState> get() = _state

    fun moveCamera(to: LatLong) {
        updateState {
            it.copy(requestedCameraLatLong = to.toPoint())
        }
    }

    fun moveCameraToUserCoordinates() {
        updateState {
            it.copy(requestedCameraLatLong = it.userCoordinates)
        }
    }

    fun setMarkerCoordinates(coordinates: List<LatLong>) {
        val markerPoints = coordinates.map { it.toPoint() }
        if (markerPoints != state.value.markerCoordinates) {
            updateState { oldState ->
                oldState.copy(
                    markerCoordinates = markerPoints,
                    isAnyMarkerUpdateAvailable = true
                )
            }
        }
    }

    fun onNewEvent(event: MapEvent) {
        when (event) {
            MapEvent.MarkersUpdated -> updateState {
                it.copy(isAnyMarkerUpdateAvailable = false)
            }

            MapEvent.CameraMovedAccordingly -> updateState {
                it.copy(requestedCameraLatLong = null)
            }

            is MapEvent.UserCoordinatesChange -> {
                if (state.value.userCoordinates == null) {
                    updateState {
                        it.copy(
                            requestedCameraLatLong = event.newCoordinates,
                            userCoordinates = event.newCoordinates
                        )
                    }
                } else {
                    _state.value.userCoordinates = event.newCoordinates
                }
            }
        }
    }

    private fun updateState(update: (MapState) -> MapState) {
        _state.value = update(_state.value)
    }
}