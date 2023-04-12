package dev.alibagherifam.hermesexpress.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

@Composable
fun MapView(
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp),
        factory = { context -> MapView(context) },
        update = { mapView ->
            mapView.getMapboxMap().apply {
                loadStyleUri(Style.MAPBOX_STREETS)
                val cameraOptions = CameraOptions.Builder()
                    .center(Point.fromLngLat(60.239, 25.004))
                    .zoom(9.0)
                    .build()
                setCamera(cameraOptions)
            }
        }
    )
}
