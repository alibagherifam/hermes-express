package dev.alibagherifam.hermesexpress.map.screen

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dev.alibagherifam.hermesexpress.map.view.LocationPermissionDialog
import dev.alibagherifam.hermesexpress.map.view.MapView
import dev.alibagherifam.hermesexpress.map.view.MyLocationButton
import kotlin.math.roundToInt

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    mapStateHolder: MapStateHolder,
    windowBottomInset: Float,
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
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 20.dp)
                    .offset {
                        IntOffset(x = 0, y = -windowBottomInset.roundToInt())
                    }
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
