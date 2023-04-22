package dev.alibagherifam.hermesexpress.deliveryoffer.domain

import java.text.DecimalFormat

class FormatCurrencyUseCase {
    operator fun invoke(value: Float): String {
        return DecimalFormat("#,##0.00").apply {
            maximumFractionDigits = 2
        }.format(value)
    }
}
