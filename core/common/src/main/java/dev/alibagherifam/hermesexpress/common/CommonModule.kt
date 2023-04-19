package dev.alibagherifam.hermesexpress.common

import dev.alibagherifam.hermesexpress.common.ui.StringProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    single {
        StringProvider { resId ->
            androidContext().getString(resId)
        }
    }
}
