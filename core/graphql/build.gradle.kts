plugins {
    alias(libs.plugins.apollo)
    alias(libs.plugins.gloom.library)
}

android {
    namespace = "dev.materii.gloom.core.graphql"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.core.commonApi)
    api(libs.bundles.apollo)
    implementation(libs.koin.core)
}

apollo {
    service("github") {
        packageName = "dev.materii.gloom.core.graphql"

        introspection {
            endpointUrl = "https://api.github.com/graphql"
            headers = mapOf(
                // GLOOM_INTROSPECTION_TOKEN Should be set to the authorization token obtained after
                // logging in to the mobile client, it should start with "gho_"
                "Authorization" to "Bearer ${System.getenv("GLOOM_INTROSPECTION_TOKEN")}",
                "User-Agent" to "Apollo GQL Introspection"
            )
            schemaFile = file("src/main/graphql/dev/materii/gloom/core/graphql/schema/github.schema.graphqls")
        }

        mapScalarToKotlinString("URI")
        mapScalarToKotlinString("HTML")
        mapScalar("Date", "kotlinx.datetime.Instant", "dev.materii.gloom.api.util.DateAdapter")
        mapScalar(
            "DateTime",
            "kotlinx.datetime.Instant",
            "com.apollographql.adapter.datetime.KotlinxInstantAdapter"
        )
    }
}