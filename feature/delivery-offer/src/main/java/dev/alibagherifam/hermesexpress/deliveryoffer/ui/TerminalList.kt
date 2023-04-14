package dev.alibagherifam.hermesexpress.deliveryoffer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.common.domain.generateFakeTerminals
import dev.alibagherifam.hermesexpress.common.theme.HermesTheme

@Composable
fun TerminalList(
    terminals: List<Terminal>,
    onTerminalClick: (Terminal) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        terminals.mapIndexed { index, terminal ->
            TerminalItem(
                number = index,
                terminal,
                onTerminalClick
            )
            if (index != terminals.lastIndex) {
                Spacer(Modifier.size(16.dp))
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
        Text(
            text = if (number == 0) "Origin:" else "Dest $number:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = terminal.postalAddress,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TerminalListPreview() {
    HermesTheme {
        TerminalList(
            terminals = generateFakeTerminals(),
            onTerminalClick = {}
        )
    }
}
