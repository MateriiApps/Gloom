plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "dev.materii.gloom.ui"

    defaultConfig {
        compileSdk = 35
        minSdk = 21
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    jvm("desktop")

    jvmToolchain(17)

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":api"))
                implementation(project(":shared"))

                implementation(libs.bundles.kotlinx)
                implementation(libs.bundles.voyager)

                api(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.runtime)

                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor3)
                implementation(libs.compose.pdf)
                implementation(libs.compose.webview.multiplatform)
                implementation(libs.highlights)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.ktor.client.core)
                implementation(libs.multiplatform.paging)
                implementation(libs.multiplatform.paging.compose)
                implementation(libs.zoomable)

                // Needed for shared module resources to work
                implementation(libs.moko.resources.compose)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.core.ktx)
                implementation(libs.coil.gif)
                implementation(libs.koin.android)
            }
        }
    }
}