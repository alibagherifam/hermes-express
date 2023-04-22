package dev.alibagherifam.hermesexpress.cloudmessaging

import dev.alibagherifam.hermesexpress.common.domain.RealTimeDeliveryOfferDatasource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val cloudMessagingModule = module {
    single { CloudMessaging(androidContext()) } withOptions {
        bind<CloudMessagingTokenDatasource>()
    }

    singleOf(::CloudMessagingDeliveryOfferDatasource) {
        bind<RealTimeDeliveryOfferDatasource>()
        named<CloudMessagingDeliveryOfferDatasource>()
    }
}
