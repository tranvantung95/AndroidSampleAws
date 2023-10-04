
plugins {
    id("amazonaws.android.library")
    id("amazonaws.android.library.jacoco")
}

android {
    namespace = "com.amazonaws.domain"
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(project(":core:model"))
    implementation(project(":core:testing"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

}