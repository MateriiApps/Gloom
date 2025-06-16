import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.apollo) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.moko.resources) apply false
    alias(libs.plugins.aboutlibraries) apply false
    alias(libs.plugins.detekt)
}

val detektFormatting = libs.detekt.formatting
val composeRules = libs.detekt.compose.rules
val detektReportMergeSarif by tasks.registering(ReportMergeTask::class) {
    output.set(layout.buildDirectory.file("reports/detekt/merged.sarif"))
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    // Detekt
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        basePath = rootProject.projectDir.toString()
        disableDefaultRuleSets = true
        config.from(rootDir.resolve("lint/detekt.yml"))

        source.from(
            "src/androidMain/kotlin",
            "src/commonMain/kotlin",
            "src/desktopMain/kotlin"
        )
    }

    dependencies {
        detektPlugins(detektFormatting)
        detektPlugins(composeRules)
        detektPlugins(project(":lint:rules"))
    }

    detektReportMergeSarif {
        input.from(tasks.withType<Detekt>().map { it.sarifReportFile })
    }

    tasks.named<Detekt>("detekt").configure {
        reports {
            sarif.required.set(true)
        }
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.layout.buildDirectory)
}