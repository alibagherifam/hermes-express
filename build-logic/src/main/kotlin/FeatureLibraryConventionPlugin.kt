import common.implementation
import common.getVersionCatalogs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class FeatureLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getVersionCatalogs()

            with(pluginManager) {
                apply("dev.alibagherifam.android.library")
                apply("dev.alibagherifam.compose")
            }

            dependencies {
                add("implementation", project(":core:common"))
                implementation(libs, "androidx.activity")
                implementation(libs, "androidx.appcompat")
                implementation(libs, "androidx.core")
                implementation(libs, "androidx.lifecycle.runtime")
                implementation(libs, "androidx.lifecycle.viewmodel")
                implementation(libs, "androidx.navigation")
                implementation(libs, "coil.compose")
                implementation(libs, "coil.core")
                implementation(libs, "koin.android")
                implementation(libs, "koin.compose")
                implementation(libs, "kotlinx.coroutines.android")
            }
        }
    }
}
