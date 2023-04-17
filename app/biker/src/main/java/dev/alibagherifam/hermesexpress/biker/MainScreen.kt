package dev.alibagherifam.hermesexpress.biker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.map.MapStateHolder
import dev.alibagherifam.hermesexpress.map.MapView
import dev.alibagherifam.hermesexpress.map.MyLocationButton
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = Unit) {
        scaffoldState.bottomSheetState.expand()
    }
    val mapStateHolder by remember { mutableStateOf(MapStateHolder()) }
    BottomSheetScaffold(
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
        val repository: DeliveryOfferRepository = koinInject()
        val offer by repository.getOfferFlow().collectAsState()
        val terminals = offer?.terminals.orEmpty()
        mapStateHolder.setMarkerCoordinates(
            coordinates = terminals.map { Pair(it.latitude, it.longitude) }
        )
        Box(Modifier.padding(paddingValues)) {
            MapView(
                state = mapStateHolder.state.value,
                onEvent = mapStateHolder::onNewEvent,
                Modifier.fillMaxSize()
            )
            MyLocationButton(
                onClick = { mapStateHolder.moveCameraToUserCoordinates() },
                sheetState = scaffoldState.bottomSheetState,
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 20.dp)
            )
        }
    }
}
