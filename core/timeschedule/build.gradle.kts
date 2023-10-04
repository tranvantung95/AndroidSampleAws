plugins {
    id("amazonaws.android.library")
}

android {
    namespace = "com.amazoneaws.timeschedule"
    defaultConfig {
        minSdk = 24
    }
}
dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.androidx.core)
}
