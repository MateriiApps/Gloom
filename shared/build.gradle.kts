@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.materiiapps.gloom.shared"

    defaultConfig {
        compileSdk = 34
        minSdk = 21
    }

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    android()
    jvmToolchain(17)

    sourceSets {
        commonMain {
            dependencies {
                api(compose.runtime)
                api(libs.bundles.kotlinx)

                api(libs.apollo.runtime)
                api(libs.apollo.normalized.cache)
                api(libs.koin.core)
                api(libs.multiplatform.settings)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.browser)
                implementation(libs.androidx.core.ktx)
            }
        }
    }
}