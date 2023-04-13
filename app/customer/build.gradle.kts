plugins {
    id("dev.alibagherifam.android.application")
    id("dev.alibagherifam.compose")
}

android {
    namespace = "dev.alibagherifam.hermesexpress.customer"

    defaultConfig {
        applicationId = "dev.alibagherifam.hermesexpress.customer"
        versionCode = 1
        versionName = "0.1.0"
    }
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.navigation)
    implementation(libs.koin.android)
}
