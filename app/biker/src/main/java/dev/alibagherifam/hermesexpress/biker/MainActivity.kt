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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dev.alibagherifam.hermesexpress.common.theme.AppTheme
import dev.alibagherifam.hermesexpress.map.MapView
import dev.alibagherifam.hermesexpress.pushnotification.subscribeForDeliveryOfferMessages
import kotlinx.coroutines.launch

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
            AppTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            MainNavHost(scaffoldState)
        }
    ) {
        MapView(Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}
