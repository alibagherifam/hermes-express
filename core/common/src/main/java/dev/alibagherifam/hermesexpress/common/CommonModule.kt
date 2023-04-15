package dev.alibagherifam.hermesexpress.common

import dev.alibagherifam.hermesexpress.common.data.DeliveryOfferStore
import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import org.koin.dsl.module

val commonModule = module {
    single<DeliveryOfferRepository> { DeliveryOfferStore() }
}
