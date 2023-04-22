package dev.alibagherifam.hermesexpress.map.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.locationcomponent.location2
import com.mapbox.maps.plugin.scalebar.scalebar
import dev.alibagherifam.hermesexpress.feature.map.R
import dev.alibagherifam.hermesexpress.map.fitCameraForLocations
import dev.alibagherifam.hermesexpress.map.getUserLocationStream
import dev.alibagherifam.hermesexpress.map.marker.MarkerManager
import dev.alibagherifam.hermesexpress.map.marker.MarkerOptions
import dev.alibagherifam.hermesexpress.map.marker.UserLocationIcon
import dev.alibagherifam.hermesexpress.map.marker.markerDefaultOptions
import dev.alibagherifam.hermesexpress.map.marker.userLocationDefaultIcon
import dev.alibagherifam.hermesexpress.map.screen.MapEvent
import dev.alibagherifam.hermesexpress.map.screen.MapState
import dev.alibagherifam.hermesexpress.map.zoomCameraOnLocation
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
    userLocationIcon: UserLocationIcon = userLocationDefaultIcon(),
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
                    val isForegroundSet = (userLocationIcon.foreground != null)
                    val isBackgroundSet = (userLocationIcon.background != null)
                    if (isForegroundSet || isBackgroundSet) {
                        locationPuck = LocationPuck2D(
                            topImage = userLocationIcon.foreground,
                            bearingImage = userLocationIcon.background,
                        )
                    }
                }
                location2.puckBearingEnabled = false
                getUserLocationStream()
                    .sample(periodMillis = 800)
                    .onEach { onEvent(MapEvent.UserLocationChange(it)) }
                    .launchIn(mapViewScope)
                markerManager.pointAnnotationManager =
                    annotations.createPointAnnotationManager()
            }
        },
        update = { mapView ->
            if (state.isAnyMarkerUpdateAvailable) {
                markerManager.deleteAllMarkers()
                if (state.markerLocations.isNotEmpty()) {
                    markerManager.addMarkers(state.markerLocations)
                    val userLocation = requireNotNull(state.userLocation)
                    mapView.fitCameraForLocations(
                        locations = state.markerLocations + userLocation
                    )
                }
                onEvent(MapEvent.MarkersUpdated)
            }
            state.requestedCameraLocation?.let { location ->
                mapView.zoomCameraOnLocation(location)
                onEvent(MapEvent.CameraMovedAccordingly)
            }
        }
    )
}
