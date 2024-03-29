import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuspendInterop
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.org.jetbrains.kotlin.native.cocoapods)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kmm.bridge)
    alias(libs.plugins.skie)
    alias(libs.plugins.ktlint)
    `maven-publish`
}

version = "0.1"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTargetVersion.get()
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // Koin
            implementation(libs.koin)

            // Shared Core Base Module
            api(project(":shared:core-base"))

            // Shared Core Network Module
            api(project(":shared:core-network"))

            // Shared Core Preferences Module
            api(project(":shared:core-preferences"))

            // Shared Core Logger Module
            api(project(":shared:core-logger"))

            // Shared Feature HomePage Module
            api(project(":shared:feature-homepage"))

            // Shared Feature Playlist Detail Module
            api(project(":shared:feature-playlist-detail"))

            // Shared Feature Album Detail Module
            api(project(":shared:feature-album-detail"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    cocoapods {
        summary = "Spotify Kmp Shared Binary"
        homepage = "https://github.com/AshuTyagi16/Spotify-KMP"
        ios.deploymentTarget = "13.0"
        extraSpecAttributes["libraries"] = "'c++', 'sqlite3'"
        license = "BSD"
        extraSpecAttributes["swift_version"] = "\"5.9.2\""
        framework {

            baseName = "shared"

            // Shared Core base Module
            export(project(":shared:core-base"))

            // Shared Core Network Module
            export(project(":shared:core-network"))

            // Shared Core Preferences Module
            export(project(":shared:core-preferences"))

            // Shared Core Logger Module
            export(project(":shared:core-logger"))

            // Shared Feature HomePage Module
            export(project(":shared:feature-homepage"))

            // Shared Feature Playlist Detail Module
            export(project(":shared:feature-playlist-detail"))

            // Shared Feature Album Detail Module
            export(project(":shared:feature-album-detail"))

            isStatic = true

            binaryOption("bundleId", "com.spotify.app.shared")
        }
    }
}

android {
    namespace = "com.spotify.app.shared"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
    }
}

kmmbridge {
    mavenPublishArtifacts()
    spm()
    cocoapods("git@github.com:AshuTyagi16/SpotifyKmpPodspec.git")
    buildType.set(NativeBuildType.DEBUG)
}

skie {
    features {
        group {
            FlowInterop.Enabled(true)
            coroutinesInterop.set(true)
            SuspendInterop.Enabled(true)
            EnumInterop.Enabled(true)
            SealedInterop.Enabled(true)
        }
    }
}

task("testClasses").doLast {
    println("This is a dummy testClasses task")
}