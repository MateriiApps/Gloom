pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "Gloom"
include(":app:android")

include(":api")
include(":shared")
include(":ui")
