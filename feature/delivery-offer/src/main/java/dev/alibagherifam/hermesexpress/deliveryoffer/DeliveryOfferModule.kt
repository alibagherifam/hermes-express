package dev.alibagherifam.hermesexpress.deliveryoffer

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.deliveryoffer.data.DeliveryOfferStore
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen.DeliveryOfferViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val deliveryOfferModule = module {
    viewModelOf(::DeliveryOfferViewModel)
    singleOf(::DeliveryOfferStore) {
        bind<DeliveryOfferRepository>()
    }
}
