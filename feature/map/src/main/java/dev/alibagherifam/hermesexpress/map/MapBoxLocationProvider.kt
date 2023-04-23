package dev.alibagherifam.hermesexpress.map

import dev.alibagherifam.hermesexpress.common.data.LocationProvider
import dev.alibagherifam.hermesexpress.common.domain.LatLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// TODO: Add actual implementation
class MapBoxLocationProvider : LocationProvider {
    override fun getUserLocationStream(): Flow<LatLong> = flow {
        emit(LatLong(35.9818, 50.7387))
    }
}
