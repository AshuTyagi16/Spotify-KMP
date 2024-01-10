@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.sqldelight)
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

            // Paging
            implementation(libs.paging)

            // SqlDelight
            implementation(libs.bundles.sqldelight.common)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // SqlDelight
            implementation(libs.bundles.sqldelight.android)

            // Koin Android
            implementation(libs.koin.android)
        }

        iosMain.dependencies {
            // SqlDelight
            implementation(libs.bundles.sqldelight.native)
        }
    }
}

val modulePackageName = "com.spotify.app.feature_playlist_detail.shared"

android {
    namespace = modulePackageName
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
    }
}

sqldelight {
    databases {
        create("PlaylistDetailDatabase") {
            packageName.set(modulePackageName)
        }
    }
}

task("testClasses").doLast {
    println("This is a dummy testClasses task")
}