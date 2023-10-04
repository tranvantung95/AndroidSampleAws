import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.example.amazonaws.configureKotlinAndroid
import com.example.amazonaws.disableUnnecessaryAndroidTests
import com.example.amazonaws.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit4").get())
                }

            }
            dependencies {
                add("implementation", libs.findLibrary("navigation-fragment").get())
            }
        }
    }
}