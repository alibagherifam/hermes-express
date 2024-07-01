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
                val mapboxSecretToken: String by settings
                username = "mapbox"
                password = mapboxSecretToken
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

rootProject.name = "hermes-express"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app:biker")
include(":app:customer")
include(":core:cloud-messaging")
include(":core:common")
include(":core:http-client")
include(":feature:delivery-offer")
include(":feature:map")
include(":feature:offering-fake-delivery")
