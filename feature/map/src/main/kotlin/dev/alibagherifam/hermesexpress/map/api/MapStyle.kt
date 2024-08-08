package dev.alibagherifam.hermesexpress.map.api

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

data class MapStyle(
    val lightStyleUri: String,
    val darkStyleUri: String
)

@Composable
internal fun getStyleForSelectedTheme(mapStyle: MapStyle) =
    if (isSystemInDarkTheme()) {
        mapStyle.darkStyleUri
    } else {
        mapStyle.lightStyleUri
    }

@Composable
internal fun defaultMapStyle(
    lightStyleUri: String = "mapbox://styles/alibagherifam/clgh6gjmg004h01o10dm9ccbi",
    darkStyleUri: String = "mapbox://styles/alibagherifam/clgh6gjmg004h01o10dm9ccbi"
) = MapStyle(
    lightStyleUri = lightStyleUri,
    darkStyleUri = darkStyleUri
)
