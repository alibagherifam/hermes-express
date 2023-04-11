package dev.alibagherifam.hermesexpress.pushnotification

import dev.alibagherifam.hermesexpress.pushnotification.network.provideHttpClient
import org.koin.dsl.module

val pushNotificationModule = module {
    single { provideHttpClient() }
}
