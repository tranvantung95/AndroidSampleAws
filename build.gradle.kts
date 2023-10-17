// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    //alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.hilt) apply false
    id("com.android.application") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
}