package dev.alibagherifam.hermesexpress.common.ui

import androidx.annotation.StringRes

fun interface StringProvider {
    fun getString(@StringRes stringResId: Int): String
}
