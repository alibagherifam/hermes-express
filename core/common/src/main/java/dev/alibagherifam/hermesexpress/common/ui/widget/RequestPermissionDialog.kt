package dev.alibagherifam.hermesexpress.common.ui.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.alibagherifam.hermesexpress.common.R
import dev.alibagherifam.hermesexpress.common.ui.LocalizationPreviews
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme

@Composable
fun RequestPermissionDialog(
    title: String,
    message: String,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = { Text(message) },
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

@LocalizationPreviews
@Composable
internal fun RequestPermissionDialogPreview() {
    HermesTheme {
        RequestPermissionDialog(
            title = stringResource(R.string.label_confirm),
            message = stringResource(R.string.message_generic_io_error),
            onConfirmClick = {},
            onDismissRequest = {}
        )
    }
}
