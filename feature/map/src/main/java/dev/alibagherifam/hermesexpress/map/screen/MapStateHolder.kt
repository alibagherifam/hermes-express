package dev.alibagherifam.hermesexpress.map.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import dev.alibagherifam.hermesexpress.common.domain.LatLong
import dev.alibagherifam.hermesexpress.map.toPoint

class MapStateHolder {
    private val _state = mutableStateOf(MapState())
    internal val state: State<MapState> get() = _state

    fun moveCamera(targetLocation: LatLong) {
        moveCamera(targetLocation.toPoint())
    }

    private fun moveCamera(
        targetLocation: Point,
        zoomLevel: Double = DEFAULT_ZOOM_LEVEL
    ) {
        val newCameraOption = CameraOptions.Builder()
            .center(targetLocation)
            .zoom(zoomLevel)
            .build()

        updateState {
            it.copy(requestedCameraOptions = newCameraOption)
        }
    }

    internal fun moveCameraToUserLocation() {
        state.value.userLocation?.let {
            moveCamera(targetLocation = it)
        }
    }

    fun setMarkerLocations(locations: List<LatLong>) {
        val markerLocations = locations.map { it.toPoint() }
        if (markerLocations == state.value.markerLocations) {
            return
        }
        updateState {
            it.copy(
                markerLocations = markerLocations,
                isAnyMarkerUpdateAvailable = true
            )
        }
    }

    internal fun onNewEvent(event: MapEvent) {
        when (event) {
            MapEvent.MarkersUpdated -> updateState {
                it.copy(isAnyMarkerUpdateAvailable = false)
            }

            MapEvent.CameraMovedAccordingly -> updateState {
                it.copy(requestedCameraOptions = null)
            }

            is MapEvent.UserLocationChange -> {
                val isFirstUserLocation = (state.value.userLocation == null)
                updateState {
                    it.copy(userLocation = event.newLocation)
                }
                if (isFirstUserLocation) {
                    moveCameraToUserLocation()
                }
            }
        }
    }

    private fun updateState(update: (MapState) -> MapState) {
        _state.value = update(_state.value)
    }

    companion object {
        const val DEFAULT_ZOOM_LEVEL = 14.0
    }
}
