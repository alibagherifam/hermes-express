package dev.alibagherifam.hermesexpress.biker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.map.screen.MapScreen
import dev.alibagherifam.hermesexpress.map.screen.MapStateHolder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    offer: DeliveryOffer?,
    onLocationPermissionDeny: () -> Unit
) {
    val terminals = offer?.terminals.orEmpty()
    val mapStateHolder = remember { MapStateHolder() }
    mapStateHolder.setMarkerLocations(
        locations = terminals.map { it.location }
    )

    val scaffoldState = rememberBottomSheetScaffoldState()
    val sheetState = scaffoldState.bottomSheetState
    if (!sheetState.hasExpandedState) {
        LaunchedEffect(key1 = Unit) {
            sheetState.expand()
        }
    }
    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContentHost(
                offer,
                onTerminalClick = { terminal ->
                    mapStateHolder.moveCamera(targetLocation = terminal.location)
                },
                scaffoldState.snackbarHostState
            )
        },
        scaffoldState = scaffoldState,
        sheetTonalElevation = 3.dp,
        sheetShadowElevation = 12.dp,
        snackbarHost = {
            Box(Modifier.fillMaxSize()) { SnackbarHost(it) }
        }
    ) { paddingValues ->
        MapScreen(
            mapStateHolder,
            bottomSheetOffset = sheetState.requireOffset(),
            onLocationPermissionDeny,
            Modifier.consumeWindowInsets(paddingValues)
        )
    }
}
