import com.android.build.api.variant.impl.VariantOutputImpl

plugins {
    alias(libs.plugins.gloom.application)
    alias(libs.plugins.gloom.application.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "dev.materii.gloom"

    defaultConfig {
        applicationId = "dev.materii.gloom"
        versionCode = 100
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            storeFile = System.getenv("SIGNING_STORE_FILE")?.let(::File)
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
        }
    }

    buildTypes {
        release {
            val hasReleaseSigning = System.getenv("SIGNING_STORE_PASSWORD")?.isNotEmpty() == true

            // versionNameSuffix = "-alpha" // TODO: Use build flavor
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            signingConfig = signingConfigs.getByName(if (hasReleaseSigning) "release" else "debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "../proguard-rules.pro")
        }

        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    androidResources {
        generateLocaleConfig = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

androidComponents {
    onVariants(selector()) { variant ->
        variant.outputs
            .map { it as VariantOutputImpl }
            .forEach { output ->
                val outputFileName = "gloom-${variant.buildType}.apk"
                output.outputFileName = outputFileName
            }
    }
}

dependencies {
    implementation(libs.bundles.kotlinx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.koin.android)
    implementation(libs.voyager.navigator)

    implementation(project(":api"))
    implementation(project(":shared"))
    implementation(project(":ui"))
}