plugins {
    id("amazonaws.android.feature")
    id("amazonaws.android.library.jacoco")
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
}