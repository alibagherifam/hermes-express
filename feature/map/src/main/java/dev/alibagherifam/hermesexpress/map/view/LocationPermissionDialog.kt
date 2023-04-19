package dev.alibagherifam.hermesexpress.map.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme
import dev.alibagherifam.hermesexpress.feature.map.R

@Composable
internal fun LocationPermissionDialog(
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.label_location_permission)) },
        text = { Text(text = stringResource(R.string.message_location_permission_required)) },
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = stringResource(R.string.label_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.label_close_app))
            }
        }
    )
}

@Preview
@Composable
internal fun LocationPermissionDialogPreview() {
    HermesTheme {
        LocationPermissionDialog(
            onConfirmClick = {},
            onDismissRequest = {}
        )
    }
}
