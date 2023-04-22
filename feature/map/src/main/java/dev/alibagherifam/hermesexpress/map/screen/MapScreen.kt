package dev.alibagherifam.hermesexpress.map.screen

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dev.alibagherifam.hermesexpress.map.view.LocationPermissionDialog
import dev.alibagherifam.hermesexpress.map.view.MapView
import dev.alibagherifam.hermesexpress.map.view.MyLocationButton

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    mapStateHolder: MapStateHolder,
    bottomSheetOffset: Float,
    onLocationPermissionDeny: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    if (locationPermissionState.status.isGranted) {
        Box(modifier) {
            MapView(
                state = mapStateHolder.state.value,
                onEvent = mapStateHolder::onNewEvent,
                Modifier.fillMaxSize()
            )
            MyLocationButton(
                onClick = { mapStateHolder.moveCameraToUserLocation() },
                bottomSheetOffset,
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 20.dp)
            )
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            LocationPermissionDialog(
                onConfirmClick = { locationPermissionState.launchPermissionRequest() },
                onDismissRequest = { onLocationPermissionDeny() }
            )
        }
    }
}
