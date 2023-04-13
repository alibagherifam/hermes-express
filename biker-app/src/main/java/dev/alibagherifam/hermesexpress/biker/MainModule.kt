package dev.alibagherifam.hermesexpress.biker

import dev.alibagherifam.hermesexpress.fakeoffer.fakeOfferModule
import org.koin.dsl.module

val mainModule = module {
    includes(fakeOfferModule)
}
