package dev.alibagherifam.hermesexpress.biker

import dev.alibagherifam.hermesexpress.cloudmessaging.cloudMessagingModule
import dev.alibagherifam.hermesexpress.common.commonModule
import dev.alibagherifam.hermesexpress.deliveryoffer.deliveryOfferModule
import dev.alibagherifam.hermesexpress.httpclient.httpClientModule
import dev.alibagherifam.hermesexpress.offeringfakedelivery.offeringFakeDeliveryModule
import org.koin.dsl.module

val mainModule = module {
    includes(commonModule)
    includes(cloudMessagingModule)
    includes(deliveryOfferModule)
    includes(httpClientModule)
    includes(offeringFakeDeliveryModule)
}
