package dev.alibagherifam.hermesexpress.map

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.alibagherifam.hermesexpress.feature.map.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLocationButton(
    onClick: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    offsetFromSheet: Dp = 70.dp
) {
    FilledIconButton(
        onClick,
        modifier.offset(
            y = with(LocalDensity.current) {
                sheetState.requireOffset().toDp() - offsetFromSheet
            }
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_my_location),
            contentDescription = stringResource(R.string.a11y_my_location_button)
        )
    }
}
