plugins {
    id("dev.alibagherifam.feature")
}

android {
    namespace = "dev.alibagherifam.hermesexpress.feature.deliveryoffer"
}

dependencies {
    implementation(project(":core:cloud-messaging"))
}
