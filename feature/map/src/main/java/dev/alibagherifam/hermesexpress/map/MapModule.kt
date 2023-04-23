package dev.alibagherifam.hermesexpress.map

import dev.alibagherifam.hermesexpress.common.data.LocationProvider
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mapModule = module {
    singleOf(::MapBoxLocationProvider) {
        bind<LocationProvider>()
    }
}
