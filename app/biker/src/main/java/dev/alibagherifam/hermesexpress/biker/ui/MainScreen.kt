package dev.alibagherifam.hermesexpress.biker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.map.LatLong
import dev.alibagherifam.hermesexpress.map.screen.MapScreen
import dev.alibagherifam.hermesexpress.map.screen.MapStateHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    markerCoordinates: List<LatLong>,
    onLocationPermissionDeny: () -> Unit
) {
    val mapStateHolder = remember { MapStateHolder() }
    mapStateHolder.setMarkerCoordinates(markerCoordinates)

    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = Unit) {
        scaffoldState.bottomSheetState.expand()
    }
    BottomSheetScaffold(
        sheetTonalElevation = 4.dp,
        sheetShadowElevation = 4.dp,
        scaffoldState = scaffoldState,
        snackbarHost = {
            Box(Modifier.fillMaxSize()) { SnackbarHost(it) }
        },
        sheetContent = {
            BottomSheetContentHost(
                scaffoldState.snackbarHostState,
                onTerminalClick = { terminal ->
                    mapStateHolder.moveCamera(
                        to = terminal.let { Pair(it.latitude, it.longitude) }
                    )
                }
            )
        }
    ) { paddingValues ->
        MapScreen(
            mapStateHolder,
            bottomSheetOffset = scaffoldState.bottomSheetState.requireOffset(),
            onLocationPermissionDeny,
            Modifier.padding(paddingValues)
        )
    }
}
