plugins {
    id("dev.alibagherifam.android.library")
    id("dev.alibagherifam.compose")
}

android {
    namespace = "dev.alibagherifam.hermesexpress.common"
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
}
