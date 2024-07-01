package dev.alibagherifam.hermesexpress.deliveryoffer.ui.screen

import dev.alibagherifam.hermesexpress.common.domain.DeliveryOffer
import dev.alibagherifam.hermesexpress.common.domain.Terminal
import dev.alibagherifam.hermesexpress.deliveryoffer.domain.FormatCurrencyUseCase
import kotlin.time.Duration

data class DeliveryOfferUiModel(
    val id: Int,
    val terminals: List<Terminal>,
    val earnings: String,
    val timeToLive: Duration,
    val estimatedDeliveryTime: String,
    val isShipmentFragile: Boolean,
    val isBoxRequired: Boolean,
    val reverseLogistics: Boolean
)

fun DeliveryOffer.toUiModel(
    formatCurrency: FormatCurrencyUseCase
) = DeliveryOfferUiModel(
    id,
    terminals,
    earnings = formatCurrency(earnings),
    timeToLive,
    estimatedDeliveryTime = estimatedDeliveryTime.inWholeMinutes.toString(),
    isShipmentFragile,
    isBoxRequired,
    reverseLogistics
)
