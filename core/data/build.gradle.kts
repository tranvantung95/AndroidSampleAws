
plugins {
   id("amazonaws.android.library")
   id("amazonaws.android.library.jacoco")
    id("amazonaws.android.hilt")
}

android {
    namespace = "com.amazonaws.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(project(":core:testing"))
    implementation(libs.core.ktx)
}