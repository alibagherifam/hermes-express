package dev.alibagherifam.hermesexpress.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location
import dev.alibagherifam.hermesexpress.feature.map.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun MapView(
    mapState: MapState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp),
        factory = { context ->
            MapView(context).apply {
                getMapboxMap().loadStyleUri(
                    styleUri = context.getString(R.string.mapbox_style_uri)
                )
                location.updateSettings {
                    enabled = true
                }
                scope.launch {
                    mapState.userCoordinate.value = locationFlow().first()
                }
            }
        },
        update = { mapView ->
            val cameraController = mapView.getMapboxMap()
            val markerCoordinates = mapState.markerCoordinates.value
            val userCoordinate = mapState.userCoordinate.value ?: return@AndroidView

            if (markerCoordinates.isEmpty()) {
                cameraController.zoomCameraOnCoordinate(userCoordinate)
            } else {
                mapView.annotations
                    .createCircleAnnotationManager()
                    .addMarkers(markerCoordinates)

                cameraController.fitCameraForCoordinates(
                    coordinates = markerCoordinates + userCoordinate
                )
            }
        }
    )
}
