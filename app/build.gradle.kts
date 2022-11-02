plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.apollographql.apollo3") version "3.6.2"
    kotlin("plugin.serialization") version "1.7.10"
}

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.materiapps.gloom"
        minSdk = 21
        targetSdk = 33
        versionCode = 100
        versionName = "1.00"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLIENT_ID", "\"Iv1.6a7241cd0f135ebd\"")
        buildConfigField("String", "CLIENT_SECRET", "\"8fddbb224bcca1eab335363beee09246e4624f54\"")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        freeCompilerArgs += "-Xcontext-receivers"
    }

    buildFeatures {
        compose = true
    }

    apollo {
        packageName.set("com.materiapps.gloom")
    }

}

dependencies {


    val composeVersion = "1.0.0-rc01"

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.browser:browser:1.4.0")
    implementation("androidx.paging:paging-compose:1.0.0-alpha16")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.compose.material3:material3:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.0")

    val koinVersion = "3.2.0"

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    val ktorVersion = "2.0.3"

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")

    val coilVersion = "2.2.1"

    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-compose:$coilVersion")

    val apolloVersion = "3.6.2"

    implementation("com.apollographql.apollo3:apollo-runtime:$apolloVersion")

    val accompanistVersion = "0.26.3-beta"

    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-webview:$accompanistVersion")

    val voyagerVersion = "1.0.0-rc02"

    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")

    implementation("com.holix.android:bottomsheetdialog-compose:1.0.1")
    implementation("com.github.jeziellago:compose-markdown:0.3.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation ("androidx.compose.ui:ui-tooling:$composeVersion")
}