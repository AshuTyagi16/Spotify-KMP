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
            baseName = "feature-homepage"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {

            // Shared Core Base Module
            implementation(project(":shared:core-base"))

            // Shared Core Network Module
            implementation(project(":shared:core-network"))

            // Store5
            implementation(libs.bundles.store)

            // Koin
            implementation(libs.koin)
            
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

val modulePackageName = "com.spotify.app.feature_homepage.shared"

android {
    namespace = modulePackageName
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
    }
}
