package dev.alibagherifam.hermesexpress.biker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.map.api.DefaultCameraSettings
import dev.alibagherifam.hermesexpress.map.api.focusCameraOnLocationAnimated
import dev.alibagherifam.hermesexpress.map.mapbox.MapboxMapState
import dev.alibagherifam.hermesexpress.map.view.MapScreen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLocationPermissionDeny: () -> Unit) {
    val repository: DeliveryOfferRepository = koinInject()
    val offer by repository.receivedOffer.collectAsState()
    val terminals = offer?.terminals.orEmpty()
    val mapState = remember {
        MapboxMapState(DefaultCameraSettings)
    }
    mapState.updateMarkerLocations(
        locations = terminals.map { it.location }
    )

    val scaffoldState = rememberBottomSheetScaffoldState()
    val sheetState = scaffoldState.bottomSheetState
    if (!sheetState.hasExpandedState) {
        LaunchedEffect(key1 = Unit) {
            sheetState.expand()
        }
    }
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContentHost(
                offer = offer,
                onTerminalClick = { terminal ->
                    scope.launch {
                        mapState.focusCameraOnLocationAnimated(terminal.location)
                    }
                },
                snackbarHostState = scaffoldState.snackbarHostState
            )
        },
        scaffoldState = scaffoldState,
        sheetTonalElevation = 3.dp,
        sheetShadowElevation = 12.dp,
        snackbarHost = {
            Box(Modifier.fillMaxSize()) { SnackbarHost(it) }
        }
    ) {
        if (LocalInspectionMode.current) {
            Surface(Modifier.fillMaxSize()) {}
            return@BottomSheetScaffold
        }
        val sheetHeight = with(LocalDensity.current) {
            val sheetOffset = sheetState.requireOffset()
            val screenHeightDp = with(LocalConfiguration.current) {
                screenHeightDp.dp.toPx()
            }
            screenHeightDp - sheetOffset
        }
        MapScreen(
            state = mapState,
            windowBottomInset = sheetHeight,
            onLocationPermissionDeny = onLocationPermissionDeny
        )
    }
}

@Preview
@Composable
internal fun MainScreenPreview() {
    HermesTheme {
        MainScreen(onLocationPermissionDeny = {})
    }
}
