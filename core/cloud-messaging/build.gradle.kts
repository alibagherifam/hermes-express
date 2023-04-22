plugins {
    id("dev.alibagherifam.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.cloudmessaging"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.datastore)
    implementation(libs.firebase.messaging)
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.properties)
    implementation(platform(libs.firebase.bom))
}
