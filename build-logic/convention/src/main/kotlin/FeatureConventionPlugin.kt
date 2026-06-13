import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class FeatureConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "gloom.library")
            apply(plugin = "gloom.library.compose")

            extensions.configure<LibraryExtension> {
                // Converts a feature module's path (i.e. :feature:settings)
                // to a valid resource prefix (i.e. settings_)
                resourcePrefix = path
                    .split("""\W""".toRegex())
                    .drop(2)
                    .distinct()
                    .joinToString(separator = "_")
                    .lowercase() + "_"
            }
        }
    }

}