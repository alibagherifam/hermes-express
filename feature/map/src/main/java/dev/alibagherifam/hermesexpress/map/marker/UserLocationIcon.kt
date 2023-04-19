package dev.alibagherifam.hermesexpress.map.marker

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import dev.alibagherifam.hermesexpress.feature.map.R

class UserLocationIcon(val foreground: Drawable?, val background: Drawable?)

@Composable
fun userLocationDefaultIcon(
    foreground: Drawable? = ContextCompat
        .getDrawable(LocalContext.current, R.drawable.ic_user_location),
    background: Drawable? = null
) = UserLocationIcon(foreground, background)
