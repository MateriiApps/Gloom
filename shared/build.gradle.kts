plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "com.materiiapps.gloom.shared"

    defaultConfig {
        compileSdk = 34
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(compose.runtime)
    api(libs.bundles.kotlinx)

    api(libs.aboutlibraries.core)
    api(libs.apollo.runtime)
    api(libs.apollo.normalized.cache)
    api(libs.koin.core)
    api(libs.multiplatform.settings)

    implementation(libs.androidx.browser)
    implementation(libs.androidx.core.ktx)
}