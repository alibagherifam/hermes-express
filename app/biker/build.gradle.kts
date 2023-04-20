plugins {
    id("dev.alibagherifam.android.application")
    id("dev.alibagherifam.compose")
    alias(libs.plugins.googleServices)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.biker"

    defaultConfig {
        applicationId = "dev.alibagherifam.hermesexpress.biker"
        versionCode = 1
        versionName = "0.1.0"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:cloud-messaging"))
    implementation(project(":feature:delivery-offer"))
    implementation(project(":feature:map"))
    implementation(project(":feature:offering-fake-delivery"))

    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.navigation)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}
