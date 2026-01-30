pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "Gloom"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":app",

    // Core
//    ":core:common",
    ":core:common-api",
    ":core:data",
    ":core:graphql",
    ":core:model",

    // Tooling
    ":lint:rules"
)

include(":api")
include(":shared")
include(":ui")