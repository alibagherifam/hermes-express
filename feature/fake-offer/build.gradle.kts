plugins {
    id("dev.alibagherifam.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.offeringfakedelivery"
}

dependencies {
    implementation(libs.firebase.messaging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.properties)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.retrofit.converter)
    implementation(libs.squareup.retrofit.core)
    implementation(platform(libs.firebase.bom))
}
