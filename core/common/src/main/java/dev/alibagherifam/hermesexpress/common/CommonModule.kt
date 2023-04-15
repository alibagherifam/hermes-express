package dev.alibagherifam.hermesexpress.common

import dev.alibagherifam.hermesexpress.common.data.DeliveryOfferStore
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::DeliveryOfferStore) {
        bind<DeliveryOfferRepository>()
    }
}
