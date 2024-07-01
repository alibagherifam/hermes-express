plugins {
    alias(libs.plugins.alibagherifam.android.feature)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.feature.deliveryoffer"
}

dependencies {
    implementation(projects.core.cloudMessaging)
}
