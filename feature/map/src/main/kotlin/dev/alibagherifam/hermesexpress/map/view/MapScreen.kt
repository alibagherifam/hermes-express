package dev.alibagherifam.hermesexpress.map.view

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.ui.widget.RequestPermissionScaffold
import dev.alibagherifam.hermesexpress.feature.map.R
import dev.alibagherifam.hermesexpress.map.mapbox.MapboxMap
import dev.alibagherifam.hermesexpress.map.mapbox.MapboxMapState
import dev.alibagherifam.hermesexpress.map.api.focusCameraOnUserLocationAnimated
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun MapScreen(
    state: MapboxMapState,
    windowBottomInset: Float,
    onLocationPermissionDeny: () -> Unit,
    modifier: Modifier = Modifier
) {
    RequestPermissionScaffold(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        rationaleDialogTitle = stringResource(R.string.label_location_permission),
        rationaleDialogMessage = stringResource(R.string.message_location_permission_required),
        onPermissionDeny = onLocationPermissionDeny
    ) {
        Box(modifier) {
            val scope = rememberCoroutineScope()
            MapboxMap(
                state = state,
                modifier = Modifier.fillMaxSize()
            )
            MyLocationButton(
                onClick = {
                    scope.launch { state.focusCameraOnUserLocationAnimated() }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 20.dp)
                    .offset {
                        IntOffset(x = 0, y = -windowBottomInset.roundToInt())
                    }
            )
        }
    }
}
