package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point
import dev.alibagherifam.hermesexpress.common.data.LocationProvider
import dev.alibagherifam.hermesexpress.common.domain.LatLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// TODO: Provide better implementation
class MapBoxLocationProvider : LocationProvider {

    var lastLocation: Point = Point.fromLngLat(50.7387, 35.9818)

    override fun getUserLocationStream(): Flow<LatLong> = flow {
        emit(LatLong(lastLocation.latitude(), lastLocation.longitude()))
    }
}
