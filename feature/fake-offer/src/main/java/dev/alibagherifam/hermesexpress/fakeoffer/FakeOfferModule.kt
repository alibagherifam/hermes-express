package dev.alibagherifam.hermesexpress.fakeoffer

import dev.alibagherifam.hermesexpress.fakeoffer.network.provideHttpClient
import org.koin.dsl.module

val fakeOfferModule = module {
    single { provideHttpClient() }
}
