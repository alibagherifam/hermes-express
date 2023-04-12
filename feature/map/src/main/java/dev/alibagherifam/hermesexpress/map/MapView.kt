package dev.alibagherifam.hermesexpress.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun MapView(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp),
        factory = { context -> MapView(context) },
        update = { mapView ->
            mapView.getMapboxMap().apply {
                loadStyleUri(Style.MAPBOX_STREETS)
            }
            mapView.location.updateSettings {
                enabled = true
            }
            scope.launch {
                mapView.zoomOnCurrentLocation()
            }
        }
    )
}

suspend fun MapView.zoomOnCurrentLocation(zoomLevel: Double = 14.0) {
    val currentLocation = locationFlow().first()
    val cameraOptions = CameraOptions.Builder()
        .center(currentLocation)
        .zoom(zoomLevel)
        .build()
    getMapboxMap().setCamera(cameraOptions)
}

fun MapView.locationFlow(): Flow<Point> = callbackFlow {
    val positionChangedListener = OnIndicatorPositionChangedListener { point ->
        trySendBlocking(point)
    }
    location.addOnIndicatorPositionChangedListener(positionChangedListener)
    awaitClose { location.removeOnIndicatorPositionChangedListener(positionChangedListener) }
}.conflate()
