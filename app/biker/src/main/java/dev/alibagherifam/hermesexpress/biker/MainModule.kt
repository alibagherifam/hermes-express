package dev.alibagherifam.hermesexpress.biker

import dev.alibagherifam.hermesexpress.deliveryoffer.deliveryOfferModule
import dev.alibagherifam.hermesexpress.offeringfakedelivery.offeringFakeDeliveryModule
import org.koin.dsl.module

val mainModule = module {
    includes(deliveryOfferModule)
    includes(offeringFakeDeliveryModule)
}
