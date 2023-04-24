package dev.alibagherifam.hermesexpress.common.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionScaffold(
    permission: String,
    rationaleDialogTitle: String,
    rationaleDialogMessage: String,
    onPermissionDeny: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = Box(modifier.fillMaxSize()) {
    val notificationPermissionState = rememberPermissionState(permission)
    if (notificationPermissionState.status.isGranted) {
        content()
    } else {
        RequestPermissionDialog(
            title = rationaleDialogTitle,
            message = rationaleDialogMessage,
            onConfirmClick = { notificationPermissionState.launchPermissionRequest() },
            onDismissRequest = onPermissionDeny
        )
    }
}
