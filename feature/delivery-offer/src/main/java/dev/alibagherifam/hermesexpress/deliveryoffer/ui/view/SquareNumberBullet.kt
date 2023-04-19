package dev.alibagherifam.hermesexpress.deliveryoffer.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SquareNumberBullet(
    number: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .size(width = 20.dp, height = 20.dp)
            .background(
                color = MaterialTheme.colorScheme.onErrorContainer,
                shape = RoundedCornerShape(6.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onError
        )
    }
}
