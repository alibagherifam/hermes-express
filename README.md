# Hermes Express

![Banner](banner.jpg)

Hermes Express is a personal project that showcases how to build a delivery Android application
using state-of-the-art technologies in Android development. The app enables bikers to explore
delivery offers and accept one to fulfill. Using modern Android development technologies (listed in
the [Technologies](#Technologies) section below), Hermes Express provides a practical example of
creating a reliable, scalable, and user-friendly app in the delivery domain.

## Technologies

### Architecture

- [Modular](https://developer.android.com/topic/modularization) by both feature and layer (feature
  and core directories)
- MVVM + [UDF](https://developer.android.com/jetpack/compose/architecture#udf) state management in
  the presentation layer
- Reactive style using [Coroutine Flow](https://kotlinlang.org/docs/coroutines-overview.html)
- [Koin](https://insert-koin.io/) for Dependency Injection
- [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

### I/O

- [FCM](https://firebase.google.com/docs/cloud-messaging) for cloud messaging
- [Retrofit](https://square.github.io/retrofit/) for HTTP request
- [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)

### Map

- [MapBox Android SDK](https://docs.mapbox.com/android/maps/guides/)
- Jetpack Compose wrapper around MapView with UDF state management

### Build

- [Gradle composite build](https://docs.gradle.org/current/userguide/composite_builds.html) for
  encapsulating build logics
- [Gradle Convention Plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html)
  for sharing build logic between subprojects
- [Version Catalog](https://docs.gradle.org/current/userguide/platforms.html) for sharing dependency
  versions between subprojects
- [Detekt](https://detekt.dev/) static analyzer for finding code smells