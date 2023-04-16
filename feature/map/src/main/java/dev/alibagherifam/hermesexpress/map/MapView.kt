package dev.alibagherifam.hermesexpress.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location
import dev.alibagherifam.hermesexpress.feature.map.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample

@OptIn(FlowPreview::class)
@Composable
fun MapView(
    stateHolder: MapStateHolder,
    modifier: Modifier = Modifier
) {
    var markerManager by remember {
        mutableStateOf<CircleAnnotationManager?>(null)
    }
    val mapViewScope = rememberCoroutineScope()
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
                    .onEach { stateHolder.onNewEvent(MapEvent.UserCoordinatesChange(it)) }
                    .launchIn(mapViewScope)
                markerManager = annotations.createCircleAnnotationManager()
            }
        },
        update = { mapView ->
            markerManager?.deleteAll()
            if (stateHolder.markerLatLongs.isNotEmpty()) {
                val markerCoordinates = stateHolder.markerLatLongs
                markerManager?.addMarkers(markerCoordinates)
                if (stateHolder.shouldFitCameraForMarkers) {
                    val userCoordinates = requireNotNull(stateHolder.userLatLong)
                    mapView.fitCameraForCoordinates(
                        coordinates = markerCoordinates + userCoordinates
                    )
                    stateHolder.onNewEvent(MapEvent.CameraFittedForMarkers)
                }
            }
            stateHolder.fixedCameraLatLong?.let { latLong ->
                mapView.zoomCameraOnCoordinate(latLong)
                stateHolder.onNewEvent(MapEvent.CameraMovedAccordingly)
            }
        }
    )
}
