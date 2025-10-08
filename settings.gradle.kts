// settings.gradle.kts
// settings.gradle.kts

pluginManagement {
    repositories {
        // Required for Android Gradle Plugin and AndroidX
        google()
        // Standard repositories
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Flashcards"
include(":app"))
