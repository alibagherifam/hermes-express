package dev.alibagherifam.hermesexpress.biker

import dev.alibagherifam.hermesexpress.fakeoffer.fakeOfferModule
import dev.alibagherifam.hermesexpress.pushnotification.pushNotificationModule
import org.koin.dsl.module

val mainModule = module {
    includes(fakeOfferModule)
    includes(pushNotificationModule)
}
