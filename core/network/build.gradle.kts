plugins {
    id("amazonaws.android.library")
    id("amazonaws.android.hilt")
}

android {
    namespace = "com.amazonaws.network"
    defaultConfig {
        android.buildFeatures.buildConfig = true
        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://jmde6xvjr4.execute-api.us-east-1.amazonaws.com\""
        )
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.gson.conveter)
    implementation(libs.gson)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}