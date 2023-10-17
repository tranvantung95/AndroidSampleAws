plugins {
    id("amazonaws.android.library")
}

android {
    namespace = "com.amazonaws.testing"
}


dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(libs.gson)
    api(libs.retrofit.core)
    api(libs.gson.conveter)
    api(libs.okhttp.logging)
    api(libs.junit)
    api(libs.junit4)
    api(libs.androidx.test.core)
    api(libs.androidx.test.ext)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.mockk)
    api(libs.mockk.android)
    api(libs.koin.test)
    api(libs.koin.test.junit4)
    api(libs.androidx.navigation.test)
    api(libs.androidx.arch.test)
    api(libs.coroutine.test)
    api(libs.turbine)
    api(libs.androidx.fragment)
    api(libs.kaspresso)
    api(libs.androidx.fragment.ktx)
    api(libs.espresso.core)
    api(libs.espresso.core)
    api(libs.fragment.testing)
    api(libs.espresso.idling)
    api(libs.mock.websever)
//    debugImplementation(libs.fragment.testing)
//    androidTestImplementation(libs.androidx.test.ext.junit)
//    androidTestImplementation(libs.androidx.test.runner)
//    androidTestImplementation(libs.androidx.test.rules)
//    androidTestImplementation(libs.kaspresso)
//    androidTestImplementation(libs.espresso.core)
//    androidTestImplementation(libs.koin.test)
//    androidTestImplementation(libs.koin.test.junit4)
}