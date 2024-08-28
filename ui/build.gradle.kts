import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.moko.resources)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "com.materiiapps.gloom.ui"

    defaultConfig {
        compileSdk = 34
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

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":api"))
                implementation(project(":shared"))

                api(libs.bundles.kotlinx)
                api(libs.bundles.voyager)

                api(compose.material3)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.runtime)

                api(libs.compose.imageloader)
                api(libs.compose.pdf)
                api(libs.highlights)
                api(libs.koin.core)
                api(libs.koin.compose)
                api(libs.moko.resources.compose)
                api(libs.multiplatform.paging)
                api(libs.multiplatform.paging.compose)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.bundles.accompanist)
                api(libs.androidx.core.ktx)
                api(libs.koin.android)
                api(libs.zoomable)
            }
        }
    }
}

multiplatformResources {
    resourcesPackage = "com.materiiapps.gloom"
    resourcesClassName = "Res"
}