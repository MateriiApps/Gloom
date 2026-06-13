plugins {
    alias(libs.plugins.gloom.library)
}

android {
    namespace = "dev.materii.gloom.core.model"
}

dependencies {
    implementation(projects.core.graphql)
    api(libs.kotlinx.datetime)
}