plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.compose") version "2.0.0" // Compose Compiler Gradle plugin required for Kotlin 2.0
    kotlin("kapt")
}

android {
    namespace = "com.example.flashcards"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.flashcards"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    // Remove kotlinCompilerExtensionVersion from composeOptions (no longer required with Kotlin 2.0 plugin)
    composeOptions {
        // No explicit kotlinCompilerExtensionVersion needed
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.09.02")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class:1.4.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Room dependencies
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Material Components for Android (optional, for XML styling)
    implementation("com.google.android.material:material:1.9.0")
}
