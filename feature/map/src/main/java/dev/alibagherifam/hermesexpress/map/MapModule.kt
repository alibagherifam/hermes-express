package dev.alibagherifam.hermesexpress.map

import dev.alibagherifam.hermesexpress.common.data.LocationProvider
import org.koin.dsl.module

val mapModule = module {
    single<LocationProvider> { MapBoxLocationProvider() }
}
