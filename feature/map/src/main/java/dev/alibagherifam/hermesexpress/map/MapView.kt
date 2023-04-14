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
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import dev.alibagherifam.hermesexpress.feature.map.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
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

fun MapView.locationFlow(): Flow<Point> = callbackFlow {
    val positionChangedListener = OnIndicatorPositionChangedListener { point ->
        trySendBlocking(point)
    }
    location.addOnIndicatorPositionChangedListener(positionChangedListener)
    awaitClose { location.removeOnIndicatorPositionChangedListener(positionChangedListener) }
}.conflate()

fun CircleAnnotationManager.addMarkers(coordinates: List<Point>) {
    val markerOptions = CircleAnnotationOptions()
        .withCircleRadius(circleRadius = 8.0)
        .withCircleColor(circleColor = "#EE4E8b")
        .withCircleStrokeWidth(circleStrokeWidth = 2.0)
        .withCircleStrokeColor(circleStrokeColor = "#FFFFFF")

    for (point in coordinates) {
        markerOptions.withPoint(point)
        create(markerOptions)
    }
}

fun MapboxMap.zoomCameraOnCoordinate(
    coordinate: Point,
    zoomLevel: Double = 14.0
) {
    val cameraOptions = CameraOptions.Builder()
        .center(coordinate)
        .zoom(zoomLevel)
        .build()
    setCamera(cameraOptions)
}

fun MapboxMap.fitCameraForCoordinates(coordinates: List<Point>) {
    val viewportPadding = EdgeInsets(100.0, 100.0, 300.0, 100.0)
    val fittedCamera = cameraForCoordinates(coordinates, viewportPadding)
    setCamera(fittedCamera)
}
