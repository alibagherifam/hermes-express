package dev.alibagherifam.hermesexpress.deliveryoffer.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.common.domain.generateFakeTerminals
import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme

@Composable
internal fun TerminalList(
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
                onTerminalClick,
                isFirstItem = (index == 0),
                isLastItem = (index == terminals.lastIndex)
            )
        }
    }
}

@Composable
internal fun TerminalItem(
    number: Int,
    terminal: Terminal,
    onClick: (Terminal) -> Unit,
    modifier: Modifier = Modifier,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    timelineWidth: Dp = 1.dp,
    timelineColor: Color = MaterialTheme.colorScheme.primary
) {
    check(!(isFirstItem && isLastItem)) { "Item can either be first or last one at a time" }
    Row(
        modifier
            .height(IntrinsicSize.Max)
            .clickable { onClick(terminal) }
    ) {
        Box(contentAlignment = Alignment.Center) {
            DashedVerticalDivider(
                Modifier.fillMaxHeight(),
                width = timelineWidth,
                color = timelineColor,
                drawHalfTop = !isFirstItem,
                drawHalfBottom = !isLastItem
            )
            SquareNumberBullet(number)
        }
        Spacer(Modifier.size(12.dp))
        Column {
            Spacer(Modifier.size(20.dp))
            Text(
                text = terminal.postalAddress,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.size(20.dp))
            if (!isLastItem) {
                Divider(Modifier.padding(start = 8.dp, end = 28.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
internal fun TerminalListPreview() {
    HermesTheme {
        val stringProvider = with(LocalContext.current) {
            StringProvider { getString(it) }
        }
        TerminalList(
            terminals = generateFakeTerminals(stringProvider),
            onTerminalClick = {}
        )
    }
}
