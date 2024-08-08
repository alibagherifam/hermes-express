import dev.alibagherifam.build.convention.libsCatalog
import dev.alibagherifam.build.convention.getLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dev.alibagherifam.android.library")
                apply("dev.alibagherifam.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                add("implementation", project(":core:common"))
                addCommonExternalDependencies(libs = libsCatalog)
            }
        }
    }

    private fun DependencyHandlerScope.addCommonExternalDependencies(libs: VersionCatalog) {
        add("implementation", libs.getLibrary("androidx.activity.compose"))
        add("implementation", libs.getLibrary("androidx.appcompat"))
        add("implementation", libs.getLibrary("androidx.core.ktx"))
        add("implementation", libs.getLibrary("androidx.lifecycle.runtime.compose"))
        add("implementation", libs.getLibrary("androidx.lifecycle.viewmodel.compose"))
        add("implementation", libs.getLibrary("androidx.navigation.compose"))
        add("implementation", libs.getLibrary("coil.compose"))
        add("implementation", libs.getLibrary("coil.core"))
        add("implementation", libs.getLibrary("koin.android"))
        add("implementation", libs.getLibrary("koin.compose"))
        add("implementation", libs.getLibrary("kotlinx.coroutines.android"))
        add("implementation", libs.getLibrary("kotlinx.serialization.json"))
    }
}
