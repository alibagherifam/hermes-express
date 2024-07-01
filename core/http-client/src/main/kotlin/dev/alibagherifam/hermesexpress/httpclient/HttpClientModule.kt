package dev.alibagherifam.hermesexpress.httpclient

import org.koin.dsl.module

val httpClientModule = module {
    single { provideHttpClient() }
}
