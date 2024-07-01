plugins {
    alias(libs.plugins.alibagherifam.android.application)
    alias(libs.plugins.alibagherifam.compose)
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
    implementation(projects.core.common)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.android)
}
