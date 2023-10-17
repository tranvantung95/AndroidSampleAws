import com.android.build.gradle.LibraryExtension
import com.example.amazonaws.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("amazonaws.android.library")
                apply("kotlin-kapt")
                apply("amazonaws.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.amazonaws.testing.AmazonawsRunner"
                }
                buildFeatures {
                    dataBinding = true
                }
                packaging {
                    resources.excludes.add("META-INF/*")
                }
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }

            dependencies {
                add("implementation", project(":core:model"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:testing"))
                add("implementation", libs.findLibrary("coil").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("appcompat").get())
                add("implementation", libs.findLibrary("coroutines.core").get())
                add("implementation", libs.findLibrary("lifecycle.ext").get())
                add("implementation", libs.findLibrary("lifecycle.viewmodel").get())
                add("implementation", libs.findLibrary("core.ktx").get())
                add("implementation", libs.findLibrary("androidx.fragment").get())
                add("implementation", libs.findLibrary("navigation-fragment").get())
                add("implementation", libs.findLibrary("androidx.fragment.ktx").get())

                add("testImplementation", project(":core:testing"))
                add("androidTestImplementation", project(":core:testing"))

                add("androidTestImplementation", libs.findLibrary("espresso.core").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-rules").get())
                add("androidTestImplementation", libs.findLibrary("androidx.test.runner").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("junit4").get())
                add("testImplementation", libs.findLibrary("androidx-test-core").get())
                add("testImplementation", libs.findLibrary("androidx-test-rules").get())
                add("testImplementation", libs.findLibrary("androidx.test.runner").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("mockk-android").get())
                add("testImplementation", libs.findLibrary("koin-test").get())
                add("testImplementation", libs.findLibrary("koin-test-junit4").get())
                add("testImplementation", libs.findLibrary("androidx-navigation-test").get())
                add("testImplementation", libs.findLibrary("androidx-arch-test").get())
                add("testImplementation", libs.findLibrary("coroutine-test").get())
                add("testImplementation", libs.findLibrary("turbine").get())

            }
        }
    }
}