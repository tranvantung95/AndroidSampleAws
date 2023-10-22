
plugins {
    id("amazonaws.android.feature")
    id("amazonaws.android.library.compose")
    id("amazonaws.android.library.jacoco")
}

android {
    namespace = "com.amazonaws.matches"
    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(project(":core:timeschedule"))
    implementation(project(":core:navigation"))
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.common)
    implementation(libs.media3.ui)
}

