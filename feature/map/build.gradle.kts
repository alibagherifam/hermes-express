plugins {
    alias(libs.plugins.alibagherifam.android.feature)
}

android {
    namespace = "dev.alibagherifam.hermesexpress.feature.map"
}

dependencies {
    implementation(libs.mapbox.core)
    implementation(libs.gms.location)
}
