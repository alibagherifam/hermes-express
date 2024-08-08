package dev.alibagherifam.hermesexpress.map.mapbox

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraBoundsOptions
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import dev.alibagherifam.hermesexpress.common.domain.Location
import dev.alibagherifam.hermesexpress.map.api.CameraSettings
import dev.alibagherifam.hermesexpress.map.api.UserLocationIcon
import dev.alibagherifam.hermesexpress.map.api.ZoomBounds
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal fun CameraSettings.toCameraOptions(): CameraOptions =
    CameraOptions.Builder()
        .center(location.toPoint())
        .zoom(zoom.value.toDouble())
        .build()

internal fun PaddingValues.toEdgeInsets(
    density: Density,
    layoutDirection: LayoutDirection
): EdgeInsets =
    with(density) {
        EdgeInsets(
            calculateTopPadding().toPx().toDouble(),
            calculateLeftPadding(layoutDirection).toPx().toDouble(),
            calculateBottomPadding().toPx().toDouble(),
            calculateRightPadding(layoutDirection).toPx().toDouble()
        )
    }

internal fun Location.toPoint(): Point = Point.fromLngLat(this.first, this.second)

internal fun Point.toLocation(): Location = Location(latitude(), longitude())

internal suspend fun MapViewportState.animate(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null,
) {
    suspendCoroutine { continuation ->
        easeTo(
            cameraOptions = cameraOptions,
            animationOptions = animationOptions,
            completionListener = { continuation.resume(Unit) }
        )
    }
}

internal fun ZoomBounds.toBoundsOptions(): CameraBoundsOptions =
    CameraBoundsOptions.Builder()
        .minZoom(this.min.value.toDouble())
        .maxZoom(this.max.value.toDouble())
        .build()

internal fun UserLocationIcon.toLocationPuck2D() =
    LocationPuck2D(
        topImage = this.foreground?.let { ImageHolder.from(it) },
        bearingImage = this.background?.let { ImageHolder.from(it) },
    )
