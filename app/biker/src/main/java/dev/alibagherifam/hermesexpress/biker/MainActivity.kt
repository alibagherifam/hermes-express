package dev.alibagherifam.hermesexpress.biker

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import dev.alibagherifam.hermesexpress.biker.ui.MainScreen
import dev.alibagherifam.hermesexpress.cloudmessaging.CloudMessaging
import dev.alibagherifam.hermesexpress.cloudmessaging.CloudMessagingDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.common.ui.widget.RequestPermissionScaffold
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import dev.alibagherifam.hermesexpress.cloudmessaging.R as CloudmessagingR

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleNotificationPayload()
        val cloudMessaging: CloudMessaging = get()
        cloudMessaging.createDeliveryOfferNotificationChannel(context = this)
        lifecycleScope.launch {
            cloudMessaging.subscribeToDeliveryOfferTopic()
        }
        setContent {
            HermesTheme { Content() }
        }
    }

    @Composable
    private fun Content() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            MainScreen(onLocationPermissionDeny = { finish() })
        } else {
            RequestPermissionScaffold(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                rationaleDialogTitle = stringResource(
                    CloudmessagingR.string.label_notification_permission
                ),
                rationaleDialogMessage = stringResource(
                    CloudmessagingR.string.message_notification_permission_required
                ),
                onPermissionDeny = { finish() }
            ) {
                MainScreen(onLocationPermissionDeny = { finish() })
            }
        }
    }

    private fun handleNotificationPayload() {
        intent.extras?.let { payload ->
            val deliveryOfferDatasource: CloudMessagingDeliveryOfferDatasource = get()
            deliveryOfferDatasource.handleMessagePayload(payload)
        }
    }
}
