package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point
import dev.alibagherifam.hermesexpress.common.data.LocationProvider
import dev.alibagherifam.hermesexpress.common.domain.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// TODO: Provide better implementation
class MapBoxLocationProvider : LocationProvider {

    var lastLocation: MutableStateFlow<Point?> = MutableStateFlow(null)

    override fun getUserLocationStream(): Flow<Location?> =
        lastLocation.map { location ->
            location?.let { Location(it.latitude(), it.longitude()) }
        }
}
