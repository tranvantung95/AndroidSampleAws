
plugins {
    id("amazonaws.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "com.amazonaws.model"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}