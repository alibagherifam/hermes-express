package dev.alibagherifam.hermesexpress.pushnotification

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import org.koin.dsl.module

val pushNotificationModule = module {
    factory { Firebase.messaging }
}
