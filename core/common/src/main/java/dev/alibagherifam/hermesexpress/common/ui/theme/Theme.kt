package dev.alibagherifam.hermesexpress.common.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color.Companion as DefaultColor

private val LightColorScheme = lightColorScheme(
    primary = BrandColor.BurntOrange,
    onPrimary = DefaultColor.White,
    primaryContainer = BrandColor.Blush,
    onPrimaryContainer = BrandColor.DarkBrown,
    secondary = BrandColor.md_theme_light_secondary,
    onSecondary = DefaultColor.White,
    secondaryContainer = BrandColor.PinkChampagne,
    onSecondaryContainer = BrandColor.DarkBrown,
    tertiary = BrandColor.md_theme_light_tertiary,
    onTertiary = DefaultColor.White,
    tertiaryContainer = BrandColor.PinkChampagne,
    onTertiaryContainer = BrandColor.DarkBrown,
    error = BrandColor.md_theme_light_error,
    errorContainer = BrandColor.PinkChampagne,
    onError = DefaultColor.White,
    onErrorContainer = BrandColor.DarkMaroon,
    background = DefaultColor.White,
    onBackground = BrandColor.DarkBrown,
    surface = DefaultColor.White,
    onSurface = BrandColor.DarkBrown,
    surfaceVariant = BrandColor.md_theme_light_surfaceVariant,
    onSurfaceVariant = BrandColor.Umber,
    outline = BrandColor.Taupe,
    inverseOnSurface = BrandColor.md_theme_light_inverseOnSurface,
    inverseSurface = BrandColor.md_theme_dark_onPrimary,
    inversePrimary = BrandColor.Apricot,
    surfaceTint = BrandColor.BurntOrange,
    outlineVariant = BrandColor.Beige,
    scrim = DefaultColor.Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandColor.Apricot,
    onPrimary = BrandColor.md_theme_dark_onPrimary,
    primaryContainer = BrandColor.md_theme_dark_primaryContainer,
    onPrimaryContainer = BrandColor.Blush,
    secondary = BrandColor.LightCoral,
    onSecondary = BrandColor.md_theme_dark_onSecondary,
    secondaryContainer = BrandColor.md_theme_dark_secondaryContainer,
    onSecondaryContainer = BrandColor.PinkChampagne,
    tertiary = BrandColor.PeachyPink,
    onTertiary = BrandColor.md_theme_dark_onTertiary,
    tertiaryContainer = BrandColor.md_theme_dark_tertiaryContainer,
    onTertiaryContainer = BrandColor.PinkChampagne,
    error = BrandColor.Salmon,
    errorContainer = BrandColor.md_theme_dark_errorContainer,
    onError = BrandColor.md_theme_dark_onError,
    onErrorContainer = BrandColor.PinkChampagne,
    background = BrandColor.DarkBrown,
    onBackground = BrandColor.Blush,
    surface = BrandColor.DarkBrown,
    onSurface = BrandColor.Blush,
    surfaceVariant = BrandColor.Umber,
    onSurfaceVariant = BrandColor.Beige,
    outline = BrandColor.TaupeGray,
    inverseOnSurface = BrandColor.DarkBrown,
    inverseSurface = BrandColor.Blush,
    inversePrimary = BrandColor.BurntOrange,
    surfaceTint = BrandColor.Apricot,
    outlineVariant = BrandColor.Umber,
    scrim = DefaultColor.Black,
)

@Composable
fun HermesTheme(
    isInDarkMode: Boolean = isSystemInDarkTheme(),
    allowDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        allowDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isInDarkMode) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        isInDarkMode -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = isInDarkMode
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = AppShapes,
        typography = AppTypography,
        content = content
    )
}
