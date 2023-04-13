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

include(":app:biker")
include(":app:customer")
include(":core:common")
include(":core:push-notification")
include(":feature:delivery-offer")
include(":feature:map")
include(":feature:offering-fake-delivery")
