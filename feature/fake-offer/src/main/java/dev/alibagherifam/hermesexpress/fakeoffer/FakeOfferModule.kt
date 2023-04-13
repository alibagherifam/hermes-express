package dev.alibagherifam.hermesexpress.fakeoffer

import dev.alibagherifam.hermesexpress.fakeoffer.data.CloudMessagingService
import dev.alibagherifam.hermesexpress.fakeoffer.network.provideHttpClient
import dev.alibagherifam.hermesexpress.fakeoffer.ui.FakeOrderOfferViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val fakeOfferModule = module {
    single { provideHttpClient() }
    factory<CloudMessagingService> { get<Retrofit>().create() }
    viewModelOf(::FakeOrderOfferViewModel)
}
