package dev.alibagherifam.hermesexpress.biker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.map.LatLong
import dev.alibagherifam.hermesexpress.map.MapState
import dev.alibagherifam.hermesexpress.map.MapView
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = Unit) {
        scaffoldState.bottomSheetState.expand()
    }
    var cameraLatLong by remember { mutableStateOf<LatLong?>(null) }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            MainNavHost(
                scaffoldState.snackbarHostState,
                onTerminalClick = { terminal ->
                    cameraLatLong = terminal.let { Pair(it.latitude, it.longitude) }
                }
            )
        }
    ) {
        val mapState = remember { MapState() }
        val repository: DeliveryOfferRepository = koinInject()
        val offer by repository.getOfferFlow().collectAsState()
        val terminals = offer?.terminals.orEmpty()

        MapView(
            mapState,
            Modifier.fillMaxSize(),
            cameraLatLong,
            markerLatLongs = terminals.map { Pair(it.latitude, it.longitude) }
        )
    }
}
