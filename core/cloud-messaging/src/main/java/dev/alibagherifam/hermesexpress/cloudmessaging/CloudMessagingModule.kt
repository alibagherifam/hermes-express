package dev.alibagherifam.hermesexpress.cloudmessaging

import dev.alibagherifam.hermesexpress.common.data.RealTimeDeliveryOfferDatasource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cloudMessagingModule = module {
    singleOf(::CloudMessaging) {
        bind<CloudMessagingTokenDatasource>()
    }

    singleOf(::CloudMessagingDeliveryOfferDatasource) {
        bind<RealTimeDeliveryOfferDatasource>()
        named<CloudMessagingDeliveryOfferDatasource>()
    }
}
