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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample

@OptIn(FlowPreview::class)
@Composable
fun MapView(
    mapState: MapState,
    modifier: Modifier = Modifier,
    markerLatLongs: List<Pair<Double, Double>> = emptyList()
) {
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
                    .sample(periodMillis = 1000)
                    .onEach { newCoordinates ->
                        if (mapState.userCoordinates == null) {
                            zoomCameraOnCoordinate(newCoordinates)
                        }
                        mapState.userCoordinates = newCoordinates
                    }.launchIn(mapViewScope)
                mapState.markerManager = annotations.createCircleAnnotationManager()
            }
        },
        update = { mapView ->
            mapState.markerManager?.deleteAll()
            if (markerLatLongs.isNotEmpty()) {
                val markerCoordinates = convertLatLongsToPoints(markerLatLongs)
                mapState.markerManager?.addMarkers(markerCoordinates)
                mapState.userCoordinates?.let { userCoordinates ->
                    mapView.fitCameraForCoordinates(
                        coordinates = markerCoordinates + userCoordinates
                    )
                }
            }
        }
    )
}
