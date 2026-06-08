plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
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
    jvmToolchain(17)

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":api"))

    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.voyager)

    api(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.runtime)

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.coil.network.ktor3)
    implementation(libs.compose.pdf)
    implementation(libs.compose.webview.multiplatform)
    implementation(libs.highlights)
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.ktor.client.core)
    implementation(libs.multiplatform.paging)
    implementation(libs.multiplatform.paging.compose)
    implementation(libs.zoomable)
}