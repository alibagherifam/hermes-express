plugins {
    id("dev.alibagherifam.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.fakeoffer"
}

dependencies {
    implementation(libs.kotlinx.serialization.properties)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.retrofit.converter)
    implementation(libs.squareup.retrofit.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}