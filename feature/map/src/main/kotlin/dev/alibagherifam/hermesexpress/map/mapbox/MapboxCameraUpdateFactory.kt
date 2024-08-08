package dev.alibagherifam.hermesexpress.map.mapbox

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

internal class MapboxCameraUpdateFactory {
    var mapView: MapView? = null
    var cameraPadding: () -> EdgeInsets = { zeroEdgeInsets }

    fun newCameraOptions(cameraOptions: CameraOptions): CameraOptions =
        cameraOptions.toBuilder()
            .padding(cameraPadding())
            .build()

    fun newCoordinateBounds(coordinates: List<Point>): CameraOptions? =
        cameraManager?.run {
            // Removing camera padding is required for
            // correct bounds calculation.
            removeCameraPadding()
            cameraForCoordinates(
                coordinates,
                cameraPadding()
            )
        }

    private val cameraManager: MapCameraManagerDelegate?
        get() = mapView?.mapboxMap
}

private val zeroEdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)

private fun MapCameraManagerDelegate.removeCameraPadding() {
    setCamera(
        cameraOptions(cameraState) { padding(zeroEdgeInsets) }
    )
}
