// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.native.cocoapods) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kmm.bridge) apply false
    alias(libs.plugins.skie) apply false
    alias(libs.plugins.sqldelight) apply false
}
buildscript {
    dependencies {
        classpath(libs.build.konfig)
    }
}

true // Needed to make the Suppress annotation work for the plugins block