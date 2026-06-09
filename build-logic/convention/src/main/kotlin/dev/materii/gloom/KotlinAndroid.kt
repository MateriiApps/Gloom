package dev.materii.gloom

import com.android.build.api.dsl.CommonExtension
import dev.materii.gloom.ext.androidSdk
import dev.materii.gloom.ext.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension

fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension
) {
    commonExtension.apply {
        compileSdk = libs.androidSdk

        defaultConfig.apply {
            minSdk = 23
        }

        compileOptions.apply {
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin<KotlinAndroidProjectExtension>()

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("android-desugar-libs").get())
    }
}

private inline fun <reified T: KotlinBaseExtension> Project.configureKotlin() = configure<T> {
    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        else -> TODO("Unsupported Kotlin project extension: $this (${this::class})")
    }.apply {
        jvmToolchain(17)
        freeCompilerArgs.addAll(
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xcontext-parameters"
        )
    }
}