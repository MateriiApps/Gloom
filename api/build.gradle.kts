import com.codingfeline.buildkonfig.compiler.FieldSpec

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.apollo)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "com.materiiapps.gloom.api"

    defaultConfig {
        compileSdk = 35
        minSdk = 21
    }
}

kotlin {
    androidTarget()
    jvm("desktop")

    jvmToolchain(17)

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared"))

                api(libs.bundles.apollo)
                implementation(libs.koin.core)
                implementation(libs.bundles.kotlinx)
                implementation(libs.bundles.ktor)
            }
        }
    }
}

apollo {
    service("service") {
        packageName = "com.materiiapps.gloom.gql"

        introspection {
            endpointUrl = "https://api.github.com/graphql"
            headers = mapOf(
                // GLOOM_INTROSPECTION_TOKEN Should be set to the authorization token obtained after
                // logging in to the mobile client, it should start with "gho_"
                "Authorization" to "Bearer ${System.getenv("GLOOM_INTROSPECTION_TOKEN")}",
                "User-Agent" to "Apollo GQL Introspection"
            )
            schemaFile = file("src/commonMain/graphql/com/materiiapps/gloom/gql/schemas/github.schema.graphqls")
        }

        mapScalarToKotlinString("URI")
        mapScalarToKotlinString("HTML")
        mapScalar("Date", "kotlinx.datetime.Instant", "com.materiiapps.gloom.api.util.DateAdapter")
        mapScalar(
            "DateTime",
            "kotlinx.datetime.Instant",
            "com.apollographql.adapter.datetime.KotlinxInstantAdapter"
        )
    }
}

buildkonfig {
    packageName = "com.materiiapps.gloom.api"
    objectName = "BuildConfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "CLIENT_ID", "M2Y4Yjg4MzRhOTFmMGNhYWQzOTI=")
        buildConfigField(
            FieldSpec.Type.STRING,
            "CLIENT_SECRET",
            "MDBlNzZmYzgzNTg4OTlkNzc5NWE0NmNkMDRhY2U4NjVmY2RjMDE2NQ=="
        )
    }
}