plugins {
    id("dev.alibagherifam.feature")
}

android {
    namespace = "dev.alibagherifam.hermesexpress.feature.map"
}

dependencies {
    implementation(libs.mapbox)
    implementation(libs.google.playServices.location)
}
