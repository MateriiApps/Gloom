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
    androidTarget()
    jvm("desktop")

    jvmToolchain(17)

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(libs.bundles.kotlinx)

                implementation(libs.apollo.runtime)
                implementation(libs.apollo.normalized.cache)
                implementation(libs.koin.core)
                implementation(libs.moko.resources.compose)
                implementation(libs.multiplatform.settings)

                api(libs.aboutlibraries.core)
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
    resourcesPackage = "dev.materii.gloom"
    resourcesClassName = "Res"
}