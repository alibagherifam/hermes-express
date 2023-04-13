package dev.alibagherifam.hermesexpress.offeringfakedelivery

import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.CloudMessagingService
import dev.alibagherifam.hermesexpress.offeringfakedelivery.network.provideHttpClient
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.OfferingFakeDeliveryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val offeringFakeDeliveryModule = module {
    single { provideHttpClient() }
    factory<CloudMessagingService> { get<Retrofit>().create() }
    viewModelOf(::OfferingFakeDeliveryViewModel)
}
