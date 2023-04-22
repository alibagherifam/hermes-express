plugins {
    id("dev.alibagherifam.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.offeringfakedelivery"
}

dependencies {
    implementation(project(":core:cloud-messaging"))

    implementation(libs.squareup.retrofit.core)
    implementation(libs.kotlinx.serialization.properties)
}
