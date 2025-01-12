import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.UUID

plugins {
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    jvmToolchain(17)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material", module = "material")
            }

            implementation(project(":api"))
            implementation(project(":shared"))
            implementation(project(":ui"))
        }
    }
}

compose.desktop.application {
    mainClass = "dev.materii.gloom.GloomKt"

    nativeDistributions {
        packageName = "Gloom"
        description = "GitHub reimagined with Material You"
        packageVersion = "0.1.0"

        modules("java.base", "java.instrument", "java.management", "java.naming", "java.prefs", "java.sql", "jdk.unsupported", "jdk.xml.dom")

        targetFormats(TargetFormat.Msi)

        windows {
            dirChooser = true
            console = true
            iconFile = file("resources/icon-windows.ico")
            upgradeUuid = UUID.randomUUID().toString()
        }

        buildTypes.release.proguard {
            configurationFiles.from(file("../proguard-rules.pro"))
            obfuscate = true
        }
    }
}

buildkonfig {
    packageName = "dev.materii.gloom"
    objectName = "BuildConfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "VERSION_NAME", "1.0.0")
    }
}