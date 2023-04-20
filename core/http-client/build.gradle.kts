plugins {
    id("dev.alibagherifam.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.httpclient"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.koin.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.retrofit.converter)
    implementation(libs.squareup.retrofit.core)
}
