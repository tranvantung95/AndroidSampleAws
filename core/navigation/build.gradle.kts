plugins {
    id("amazonaws.android.library")
}

android {
    namespace = "com.amazonaws.navigation"
    defaultConfig {
        minSdk = 24
    }
}
dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.androidx.core)
    implementation("com.google.android.material:material:1.9.0")
}