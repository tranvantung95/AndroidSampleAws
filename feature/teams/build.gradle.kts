plugins {
    id("amazonaws.android.feature")
    id("amazonaws.android.library.jacoco")
    id("amazonaws.android.library.compose")
}

android {
    namespace = "com.amazonawsteams"
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(libs.androidx.activity.compose)
}