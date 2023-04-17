package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.common.domain.generateFakeTerminals
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme

@Composable
fun TerminalList(
    terminals: List<Terminal>,
    onTerminalClick: (Terminal) -> Unit,
    modifier: Modifier = Modifier,
    showInCompactMode: Boolean = false
) {
    val indices: List<Int> = if (showInCompactMode) {
        listOf(0, terminals.lastIndex)
    } else {
        terminals.indices.toList()
    }
    Column(modifier) {
        for (index in indices) {
            TerminalItem(
                number = index + 1,
                terminals[index],
                onTerminalClick
            )
            if (index != terminals.lastIndex) {
                Divider(
                    Modifier.padding(
                        horizontal = 28.dp,
                        vertical = 20.dp
                    )
                )
            }
        }
    }
}

@Composable
fun TerminalItem(
    number: Int,
    terminal: Terminal,
    onClick: (Terminal) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.clickable { onClick(terminal) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 20.dp, height = 20.dp)
                .background(
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onError
            )
        }
        Spacer(Modifier.size(12.dp))
        Text(
            text = terminal.postalAddress,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TerminalListPreview() {
    HermesTheme {
        TerminalList(
            terminals = generateFakeTerminals(),
            onTerminalClick = {}
        )
    }
}
