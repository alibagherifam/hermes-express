plugins {
    alias(libs.plugins.alibagherifam.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.httpclient"
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.core)
    implementation(libs.retrofit.converter.kotlin)
    implementation(libs.retrofit.core)
}
