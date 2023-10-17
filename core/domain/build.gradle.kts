
plugins {
    id("amazonaws.android.library")
    id("amazonaws.android.library.jacoco")
    id("amazonaws.android.hilt")
}

android {
    namespace = "com.amazonaws.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:testing"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

}