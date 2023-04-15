package dev.alibagherifam.hermesexpress.deliveryoffer

import dev.alibagherifam.hermesexpress.deliveryoffer.ui.DeliveryOfferViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val deliveryOfferModule = module {
    viewModelOf(::DeliveryOfferViewModel)
}
