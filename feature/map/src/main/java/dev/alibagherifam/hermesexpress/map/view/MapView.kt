package dev.alibagherifam.hermesexpress.map.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar
import dev.alibagherifam.hermesexpress.feature.map.R
import dev.alibagherifam.hermesexpress.map.fitCameraForCoordinates
import dev.alibagherifam.hermesexpress.map.locationFlow
import dev.alibagherifam.hermesexpress.map.marker.MarkerManager
import dev.alibagherifam.hermesexpress.map.marker.MarkerOptions
import dev.alibagherifam.hermesexpress.map.marker.markerDefaultOptions
import dev.alibagherifam.hermesexpress.map.screen.MapEvent
import dev.alibagherifam.hermesexpress.map.screen.MapState
import dev.alibagherifam.hermesexpress.map.zoomCameraOnCoordinate
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample

@OptIn(FlowPreview::class)
@Composable
internal fun MapView(
    state: MapState,
    onEvent: (MapEvent) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes userLocationIcon: Int = R.drawable.img_wind_rose,
    markerOptions: MarkerOptions = markerDefaultOptions()
) {
    val mapViewScope = rememberCoroutineScope()
    val markerManager = remember { MarkerManager(markerOptions) }
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp),
        factory = { context ->
            MapView(context).apply {
                getMapboxMap().loadStyleUri(
                    styleUri = context.getString(R.string.mapbox_style_uri)
                )
                scalebar.enabled = false
                location.updateSettings {
                    enabled = true
                    locationPuck = LocationPuck2D(
                        ContextCompat.getDrawable(context, userLocationIcon)
                    )
                }
                locationFlow()
                    .sample(periodMillis = 800)
                    .onEach { onEvent(MapEvent.UserCoordinatesChange(it)) }
                    .launchIn(mapViewScope)
                markerManager.pointAnnotationManager =
                    annotations.createPointAnnotationManager()
            }
        },
        update = { mapView ->
            if (state.isAnyMarkerUpdateAvailable) {
                markerManager.deleteAllMarkers()
                if (state.markerCoordinates.isNotEmpty()) {
                    markerManager.addMarkers(state.markerCoordinates)
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
