import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.aboutlibraries)
    alias(libs.plugins.moko.resources)
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
    androidTarget()
    jvm("desktop")

    jvmToolchain(17)

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(compose.runtime)
                api(libs.bundles.kotlinx)

                api(libs.aboutlibraries.core)
                api(libs.apollo.runtime)
                api(libs.apollo.normalized.cache)
                api(libs.koin.core)
                api(libs.moko.resources.compose)
                api(libs.multiplatform.settings)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.browser)
                implementation(libs.androidx.core.ktx)
            }
        }
    }
}

multiplatformResources {
    resourcesPackage = "com.materiiapps.gloom"
    resourcesClassName = "Res"
}