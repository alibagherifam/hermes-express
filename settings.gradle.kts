pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                val mapBoxSecretToken: String by settings
                username = "mapbox"
                password = mapBoxSecretToken
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}
rootProject.name = "hermes-express"

include(":biker-app")
include(":common")
include(":feature:order")
include(":feature:map")
include(":feature:push-notification")
include(":feature:fake-offer")
