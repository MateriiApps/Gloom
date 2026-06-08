plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "dev.materii.gloom.shared"

    defaultConfig {
        compileSdk = 35
        minSdk = 21
    }

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(compose.runtime)
    implementation(libs.bundles.kotlinx)

    implementation(libs.apollo.runtime)
    implementation(libs.apollo.normalized.cache)
    implementation(libs.koin.core)
    implementation(libs.multiplatform.settings)

    api(libs.aboutlibraries.core)

    implementation(libs.androidx.browser)
    implementation(libs.androidx.core.ktx)
}