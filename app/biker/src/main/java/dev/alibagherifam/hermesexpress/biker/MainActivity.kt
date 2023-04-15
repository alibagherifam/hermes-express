package dev.alibagherifam.hermesexpress.biker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dev.alibagherifam.hermesexpress.common.theme.HermesTheme
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
            HermesTheme {
                MainScreen()
            }
        }
    }
}
