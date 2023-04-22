package dev.alibagherifam.hermesexpress.deliveryoffer

import dev.alibagherifam.hermesexpress.cloudmessaging.CloudMessagingDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.common.domain.RealTimeDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.deliveryoffer.data.DoubleDatasourceDeliveryOfferRepository
import dev.alibagherifam.hermesexpress.deliveryoffer.data.LongPollingDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.DeliveryOfferRepository
import dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen.DeliveryOfferViewModel
import kotlinx.coroutines.GlobalScope
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val deliveryOfferModule = module {
    viewModelOf(::DeliveryOfferViewModel)
    singleOf(::LongPollingDeliveryOfferDatasource) {
        bind<RealTimeDeliveryOfferDatasource>()
        named<LongPollingDeliveryOfferDatasource>()
    }

    single<DeliveryOfferRepository> {
        DoubleDatasourceDeliveryOfferRepository(
            coroutineScope = GlobalScope,
            dataSource1 = get(qualifier = named<CloudMessagingDeliveryOfferDatasource>()),
            dataSource2 = get(qualifier = named<LongPollingDeliveryOfferDatasource>())
        )
    }
}
