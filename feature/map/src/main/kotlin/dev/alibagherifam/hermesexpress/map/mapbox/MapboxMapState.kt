package dev.alibagherifam.hermesexpress.map.mapbox

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import dev.alibagherifam.hermesexpress.common.domain.Location
import dev.alibagherifam.hermesexpress.map.api.CameraSettings
import dev.alibagherifam.hermesexpress.map.api.MapState

class MapboxMapState(
    initialCameraSettings: CameraSettings,
) : MapState() {
    internal val cameraUpdateFactory = MapboxCameraUpdateFactory()

    internal val cameraState = MapViewportState().apply {
        val cameraUpdate = cameraUpdateFactory.newCameraOptions(
            initialCameraSettings.toCameraOptions()
        )
        setCameraOptions(cameraUpdate)
    }

    override suspend fun animateCamera(cameraSettings: CameraSettings) {
        val cameraUpdate = cameraUpdateFactory.newCameraOptions(
            cameraSettings.toCameraOptions()
        )
        cameraState.animate(cameraUpdate)
    }

    override suspend fun boundCameraAnimated(locations: List<Location>) {
        val cameraOptions = createBoundingCameraOptions(locations) ?: return
        cameraState.animate(cameraOptions)
    }

    override fun boundCamera(locations: List<Location>) {
        val cameraOptions = createBoundingCameraOptions(locations) ?: return
        cameraState.setCameraOptions(cameraOptions)
    }

    private fun createBoundingCameraOptions(locations: List<Location>): CameraOptions? {
        val coordinates = locations.map { it.toPoint() }
        return cameraUpdateFactory.newCoordinateBounds(coordinates)
    }
}
