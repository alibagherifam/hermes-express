package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.alibagherifam.hermesexpress.common.theme.AppTheme

@Composable
fun ProgressButton(
    onClick: () -> Unit,
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick,
        Modifier.wrapContentSize(),
        contentPadding = PaddingValues()
    ) {
        Box(modifier.sizeIn(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight)) {
            LinearProgressIndicator(
                progress,
                Modifier.matchParentSize(),
                trackColor = MaterialTheme.colorScheme.inversePrimary
            )
            Row(
                Modifier
                    .align(Alignment.Center)
                    .padding(ButtonDefaults.ContentPadding)
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun ProgressButtonPreview() {
    AppTheme {
        ProgressButton(
            progress = 0.3f,
            onClick = {}
        ) {
            Text(text = "Accept Offer")
        }
    }
}
