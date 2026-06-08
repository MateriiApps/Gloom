plugins {
    alias(libs.plugins.gloom.library)
    alias(libs.plugins.gloom.library.compose)
}

android {
    namespace = "dev.materii.gloom.core.icons"
}

dependencies {
    api(libs.compose.material.icons.extended)
}