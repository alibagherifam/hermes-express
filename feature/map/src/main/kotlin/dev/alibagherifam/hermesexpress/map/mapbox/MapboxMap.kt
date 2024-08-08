package dev.alibagherifam.hermesexpress.map.mapbox

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.locationcomponent.location
import dev.alibagherifam.hermesexpress.map.api.MapStyle
import dev.alibagherifam.hermesexpress.map.api.MarkerOptions
import dev.alibagherifam.hermesexpress.map.api.UserLocationIcon
import dev.alibagherifam.hermesexpress.map.api.defaultMapStyle
import dev.alibagherifam.hermesexpress.map.api.getStyleForSelectedTheme
import dev.alibagherifam.hermesexpress.map.api.isEmpty
import dev.alibagherifam.hermesexpress.map.api.markerDefaultOptions
import dev.alibagherifam.hermesexpress.map.api.toPointAnnotationOptions
import dev.alibagherifam.hermesexpress.map.api.userLocationDefaultIcon
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample

@OptIn(FlowPreview::class)
@Composable
internal fun MapboxMap(
    state: MapboxMapState,
    modifier: Modifier = Modifier,
    userLocationIcon: UserLocationIcon = userLocationDefaultIcon(),
    markerOptions: MarkerOptions = markerDefaultOptions(),
    mapStyle: MapStyle = defaultMapStyle()
) {
    val scope = rememberCoroutineScope()
    MapboxMap(
        modifier = modifier.sizeIn(minHeight = 300.dp, minWidth = 300.dp),
        scaleBar = {},
        compass = {},
        style = {
            val styleUri = getStyleForSelectedTheme(mapStyle)
            MapStyle(styleUri)
        },
    ) {
        MapEffect(Unit) { mapView ->
            state.cameraUpdateFactory.mapView = mapView
            mapView.location.updateSettings {
                enabled = true
                if (!userLocationIcon.isEmpty()) {
                    locationPuck = userLocationIcon.toLocationPuck2D()
                }
            }
            mapView.location.puckBearingEnabled = false
            mapView.location.getUserLocationStream()
                .sample(periodMillis = 800)
                .onEach { state.updateUserLocation(it.toLocation()) }
                .launchIn(scope)
        }

        val baseAnnotationOptions = remember(markerOptions) {
            markerOptions.toPointAnnotationOptions()
        }

        PointAnnotationGroup(
            annotations = state.markerLocations.value.mapIndexed { index, location ->
                baseAnnotationOptions
                    .withPoint(location.toPoint())
                    .withTextField((index + 1).toString())
            }
        )
    }
}
