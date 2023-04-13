package dev.alibagherifam.hermesexpress.fakeoffer

import dev.alibagherifam.hermesexpress.fakeoffer.data.FakeOrderOfferingService
import dev.alibagherifam.hermesexpress.fakeoffer.network.provideHttpClient
import dev.alibagherifam.hermesexpress.fakeoffer.ui.FakeOrderOfferViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val fakeOfferModule = module {
    single { provideHttpClient() }
    factory<FakeOrderOfferingService> { get<Retrofit>().create() }
    viewModel {
        FakeOrderOfferViewModel(get(), get())
    }
}
