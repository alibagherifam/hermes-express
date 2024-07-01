plugins {
    alias(libs.plugins.alibagherifam.android.application)
    alias(libs.plugins.alibagherifam.compose)
    alias(libs.plugins.gms)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.biker"

    defaultConfig {
        applicationId = "dev.alibagherifam.hermesexpress.biker"
        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    implementation(projects.core.cloudMessaging)
    implementation(projects.core.common)
    implementation(projects.core.httpClient)
    implementation(projects.feature.deliveryOffer)
    implementation(projects.feature.map)
    implementation(projects.feature.offeringFakeDelivery)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}
