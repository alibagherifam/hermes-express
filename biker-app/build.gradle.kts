plugins {
    id("dev.alibagherifam.android.application")
    id("dev.alibagherifam.compose")
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
    implementation(project(":common"))
    implementation(project(":feature:order"))

    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.navigation)
    implementation(libs.koin.android)
}
/*
packagingOptions {
    resources {
        excludes += '/META-INF/{AL2.0,LGPL2.1}'
    }
}*/
