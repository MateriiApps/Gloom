plugins {
    alias(libs.plugins.gloom.library)
}

android {
    namespace = "dev.materii.gloom.core.common.api"
}

dependencies {
    api(libs.apollo.runtime)
}