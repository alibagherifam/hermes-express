package dev.alibagherifam.hermesexpress.deliveryoffer.ui.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme

@Composable
internal fun ProgressButton(
    progress: Float,
    onPressStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    pressedStateColorSaturation: Float = 0.2f,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    LaunchedEffect(key1 = isPressed) {
        onPressStateChange(isPressed)
    }
    val containerColor = with(MaterialTheme.colorScheme) {
        if (isPressed) {
            val fraction = pressedStateColorSaturation
                .plus(0.3f)
                .coerceAtMost(1f)
            lerp(inversePrimary, onErrorContainer, fraction)
        } else {
            primary
        }
    }
    Button(
        onClick = {},
        Modifier.wrapContentSize(),
        isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        interactionSource = interactionSource,
        contentPadding = PaddingValues()
    ) {
        Box(modifier.sizeIn(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight)) {
            if (!isPressed) {
                LinearProgressIndicator(
                    progress,
                    Modifier.matchParentSize(),
                    trackColor = MaterialTheme.colorScheme.inversePrimary
                )
            }
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
internal fun ProgressButtonPreview() {
    HermesTheme {
        ProgressButton(
            progress = 0.4f,
            onPressStateChange = {}
        ) {
            Text("Progress Button")
        }
    }
}
