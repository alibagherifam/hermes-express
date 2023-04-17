package dev.alibagherifam.hermesexpress.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location
import dev.alibagherifam.hermesexpress.feature.map.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample

@OptIn(FlowPreview::class)
@Composable
internal fun MapView(
    state: MapState,
    onEvent: (MapEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val mapViewScope = rememberCoroutineScope()
    val markerManagerWrapper = remember { MarkerManagerWrapper(value = null) }
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp),
        factory = { context ->
            MapView(context).apply {
                getMapboxMap().loadStyleUri(
                    styleUri = context.getString(R.string.mapbox_style_uri)
                )
                location.updateSettings { enabled = true }
                locationFlow()
                    .sample(periodMillis = 800)
                    .onEach { onEvent(MapEvent.UserCoordinatesChange(it)) }
                    .launchIn(mapViewScope)
                markerManagerWrapper.value = annotations.createCircleAnnotationManager()
            }
        },
        update = { mapView ->
            if (state.isAnyMarkerUpdateAvailable) {
                val markerManager = markerManagerWrapper.value
                markerManager?.deleteAll()
                if (state.markerCoordinates.isNotEmpty()) {
                    markerManager?.addMarkers(state.markerCoordinates)
                    val userCoordinates = requireNotNull(state.userCoordinates)
                    mapView.fitCameraForCoordinates(
                        coordinates = state.markerCoordinates + userCoordinates
                    )
                }
                onEvent(MapEvent.MarkersUpdated)
            }
            state.requestedCameraLatLong?.let { latLong ->
                mapView.zoomCameraOnCoordinate(latLong)
                onEvent(MapEvent.CameraMovedAccordingly)
            }
        }
    )
}
