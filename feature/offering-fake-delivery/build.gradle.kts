plugins {
    alias(libs.plugins.alibagherifam.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.offeringfakedelivery"
}

dependencies {
    implementation(projects.core.cloudMessaging)

    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.properties)
}
