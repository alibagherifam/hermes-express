package dev.alibagherifam.hermesexpress.order.domain

import java.text.DecimalFormat

fun formatCurrency(price: Float): String {
    return DecimalFormat("$#,##0.00").apply {
        maximumFractionDigits = 2
    }.format(price)
}
