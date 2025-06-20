pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "Gloom"
include(":app")

include(":api")
include(":shared")
include(":ui")

include(":lint:rules")