package dev.alibagherifam.hermesexpress.biker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.common.theme.HermesTheme
import dev.alibagherifam.hermesexpress.map.MapState
import dev.alibagherifam.hermesexpress.map.MapView
import dev.alibagherifam.hermesexpress.pushnotification.subscribeForDeliveryOfferMessages
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            TODO("Show Rationale")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationPermissionStatus = ContextCompat
            .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        lifecycleScope.launch {
            subscribeForDeliveryOfferMessages()
        }
        setContent {
            HermesTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val mapState = remember { MapState() }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            val repository: DeliveryOfferRepository = koinInject()
            val offer by repository.offer.collectAsState()
            MainNavHost(
                offer,
                mapState,
                scaffoldState,
                onAcceptOfferClick = {
                    repository.clearSavedOffer()
                }
            )
        }
    ) {
        MapView(mapState, Modifier.fillMaxSize())
    }
}
