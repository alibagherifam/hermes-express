package dev.alibagherifam.hermesexpress.map.api

import dev.alibagherifam.hermesexpress.common.domain.Location

data class CameraSettings(
    val location: Location,
    val zoom: ZoomLevel
)

val DefaultCameraSettings: CameraSettings
    get() = CameraSettings(
        location = Location(
            first = 35.6972215,
            second = 51.3477741
        ),
        zoom = ZoomLevel.NORMAL
    )
