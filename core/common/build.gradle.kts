plugins {
    id("amazonaws.android.library")
    id("kotlin-kapt")
}

android {
    namespace = "com.amazonaws.common"

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.coil)
    implementation(libs.lifecycle.ext)
    implementation(libs.appcompat)
    implementation(libs.okInflater)
}