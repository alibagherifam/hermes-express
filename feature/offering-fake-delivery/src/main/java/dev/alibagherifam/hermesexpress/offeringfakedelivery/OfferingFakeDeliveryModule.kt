package dev.alibagherifam.hermesexpress.offeringfakedelivery

import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.CloudMessagingService
import dev.alibagherifam.hermesexpress.offeringfakedelivery.data.OfferingFakeDeliveryRepository
import dev.alibagherifam.hermesexpress.offeringfakedelivery.ui.screen.OfferingFakeDeliveryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val offeringFakeDeliveryModule = module {
    factory<CloudMessagingService> { get<Retrofit>().create() }
    factoryOf(::OfferingFakeDeliveryRepository)
    viewModelOf(::OfferingFakeDeliveryViewModel)
}
