import com.android.build.api.dsl.LibraryExtension
import dev.materii.gloom.configureKotlinAndroid
import dev.materii.gloom.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class LibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                // Converts a module's path (i.e. :core:ui)
                // to a valid resource prefix (i.e. core_ui_)
                resourcePrefix = path
                    .split("""\W""".toRegex())
                    .drop(1)
                    .distinct()
                    .joinToString(separator = "_")
                    .lowercase() + "_"
            }

            dependencies {
                "testImplementation"(libs.findLibrary("kotlin.test").get())
            }
        }
    }

}