import com.android.build.api.dsl.ApplicationExtension
import dev.materii.gloom.configureKotlinAndroid
import dev.materii.gloom.ext.androidSdk
import dev.materii.gloom.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class ApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.androidSdk
            }
        }
    }

}