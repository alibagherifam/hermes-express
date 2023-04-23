package dev.alibagherifam.hermesexpress.deliveryoffer.data

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.data.RealTimeDeliveryOfferDatasource
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.DeliveryOfferRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

internal class DoubleDatasourceDeliveryOfferRepository(
    coroutineScope: CoroutineScope,
    dataSource1: RealTimeDeliveryOfferDatasource,
    dataSource2: RealTimeDeliveryOfferDatasource
) : DeliveryOfferRepository {
    private val _receivedOffer = MutableStateFlow<DeliveryOffer?>(null)
    override val receivedOffer: StateFlow<DeliveryOffer?>
        get() = _receivedOffer

    init {
        merge(
            dataSource1.incomingOfferStream,
            dataSource2.incomingOfferStream
        ).conflate()
            .filter { isOfferConsumed() }
            .filter { it.isDuplicate() }
            .onEach { _receivedOffer.value = it }
            .launchIn(coroutineScope)
    }

    private fun isOfferConsumed(): Boolean = (receivedOffer.value == null)
    private fun DeliveryOffer.isDuplicate(): Boolean = (this != receivedOffer.value)

    override suspend fun acceptOffer() {
        delay(1500)
        _receivedOffer.value = null
    }

    override suspend fun ignoreOffer() {
        _receivedOffer.value = null
    }
}
