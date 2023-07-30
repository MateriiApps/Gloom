import com.codingfeline.buildkonfig.compiler.FieldSpec

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.apollo)
    alias(libs.plugins.buildkonfig)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

android {
    namespace = "com.materiiapps.gloom.api"

    defaultConfig {
        compileSdk = 34
        minSdk = 21
    }
}

kotlin {
    android()
    jvmToolchain(17)

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.bundles.apollo)
                implementation(libs.koin.core)
                implementation(libs.bundles.kotlinx)
                implementation(libs.bundles.ktor)

                implementation(project(":shared"))
            }
        }
    }
}

apollo {
    packageName.set("com.materiiapps.gloom.gql")

    mapScalarToKotlinString("URI")
    mapScalarToKotlinString("HTML")
    mapScalar("Date", "kotlinx.datetime.Instant", "com.materiiapps.gloom.api.utils.DateAdapter")
    mapScalar("DateTime", "kotlinx.datetime.Instant", "com.apollographql.apollo3.adapter.KotlinxInstantAdapter")
}

buildkonfig {
    packageName = "com.materiiapps.gloom.api"
    objectName = "BuildConfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "CLIENT_ID", "M2Y4Yjg4MzRhOTFmMGNhYWQzOTI=")
        buildConfigField(FieldSpec.Type.STRING, "CLIENT_SECRET", "MDBlNzZmYzgzNTg4OTlkNzc5NWE0NmNkMDRhY2U4NjVmY2RjMDE2NQ==")
    }
}