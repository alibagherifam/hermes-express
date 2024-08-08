package dev.alibagherifam.hermesexpress.map.api

enum class ZoomLevel(val value: Float) {
    LOWEST(3.0f),
    STREET(4.0f),
    FOCUSED(12.8f),
    NORMAL(15.4f),
    COUNTRY(18.0f),
    HIGHEST(21.0f)
}

data class ZoomBounds(
    val min: ZoomLevel,
    val max: ZoomLevel
)

val fullZoomBounds = ZoomBounds(
    min = ZoomLevel.LOWEST,
    max = ZoomLevel.HIGHEST
)
