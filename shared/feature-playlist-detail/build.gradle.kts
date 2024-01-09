@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTargetVersion.get()
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "feature-playlist-detail"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {

            // Shared Core Base Module
            api(project(":shared:core-base"))

            // Shared Core Network Module
            api(project(":shared:core-network"))

            // Shared Core Preferences Module
            implementation(project(":shared:core-preferences"))

            // Shared Core Logger Module
            implementation(project(":shared:core-logger"))

            // Store5
            implementation(libs.bundles.store)

            // Koin
            implementation(libs.koin)


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // Koin Android
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "com.spotify.app.feature_playlist_detail.shared"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
    }
}
