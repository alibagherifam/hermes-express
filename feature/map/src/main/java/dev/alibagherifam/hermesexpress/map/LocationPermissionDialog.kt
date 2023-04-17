package dev.alibagherifam.hermesexpress.map

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
fun LocationPermissionDialog(
    title: String = stringResource(R.string.label_location_permission),
    message: String = stringResource(R.string.message_location_permission_required),
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = { Text(text = message) },
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
fun LocationPermissionDialogPreview() {
    HermesTheme {
        LocationPermissionDialog(
            onConfirmClick = {},
            onDismissRequest = {}
        )
    }
}
