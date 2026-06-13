plugins {
    `kotlin-dsl`
}

group = "dev.materii.gloom.buildlogic"

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.plugins.android.application)
    compileOnly(libs.plugins.android.library)
    compileOnly(libs.plugins.kotlin.compose)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("applicationCompose") {
            id = libs.plugins.gloom.application.compose.get().pluginId
            implementationClass = "ApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.gloom.application.asProvider().get().pluginId
            implementationClass = "ApplicationConventionPlugin"
        }

        register("composeLibrary") {
            id = libs.plugins.gloom.library.compose.get().pluginId
            implementationClass = "ComposeLibraryConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.gloom.library.asProvider().get().pluginId
            implementationClass = "LibraryConventionPlugin"
        }

        register("feature") {
            id = libs.plugins.gloom.feature.get().pluginId
            implementationClass = "FeatureConventionPlugin"
        }
    }
}

fun DependencyHandler.compileOnly(dependency: Provider<PluginDependency>) {
    compileOnly(dependency.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" })
}