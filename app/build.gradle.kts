plugins {
    id("amazonaws.android.application")
    id("amazonaws.android.compose")
    id("amazonaws.android.application.jacoco")
    id("amazonaws.android.hilt")
    id("kotlin-kapt")
    id("jacoco")
}

android {
    namespace = "com.example.amazonaws"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.amazonaws"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:timeschedule"))
    implementation(project(":feature:teams"))
    implementation(project(":feature:matches"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.testharness)
    implementation(libs.androidx.compose.runtime)


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

}