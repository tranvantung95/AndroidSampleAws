plugins {
    id("amazonaws.android.application")
    id("kotlin-kapt")
    id("amazonaws.android.application.jacoco")
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
    implementation(project(":core:navigation"))
    implementation(project(":core:timeschedule"))
    implementation(project(":feature:teams"))
    implementation(project(":feature:matches"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    // androidTestImplementation(libs.androidx.navigation.test)

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //testImplementation("junit:junit:4.13.2")
    //androidTestImplementation("androidx.test.ext:junit:1.1.5")
    //androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}