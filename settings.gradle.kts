pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "Gloom"
include(":app:android")
include(":app:desktop")

include(":api")
include(":shared")
include(":ui")

include(":lint:rules")