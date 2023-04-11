plugins {
    `kotlin-dsl`
}

group = "dev.alibagherifam.hermessexpress.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "dev.alibagherifam.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "dev.alibagherifam.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = "dev.alibagherifam.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("featureLibrary") {
            id = "dev.alibagherifam.feature"
            implementationClass = "FeatureLibraryConventionPlugin"
        }
    }
}