import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.build.konfig.get().toString())
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
            baseName = "core-network"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            // Stdlib
            implementation(libs.kotlin.stdlib)

            // coroutine
            implementation(libs.coroutines.core)

            // Ktor
            implementation(libs.bundles.ktor.common)

            // Core-Logger Module
            implementation(project(":shared:core-logger"))

            // Core-Preferences Module
            implementation(project(":shared:core-preferences"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // Ktor (OkHttp Engine)
            implementation(libs.bundles.ktor.android)
        }

        iosMain.dependencies {
            // Ktor (Darwin Engine)
            implementation(libs.bundles.ktor.ios)
        }

    }
}

val modulePackageName = "com.spotify.app.core_network.shared"

buildkonfig {
    packageName = modulePackageName
     exposeObjectWithName = "CoreNetworkBuildKonfig"

    defaultConfigs {
        buildConfigField(STRING, "BASE_URL", "api.spotify.com")
        buildConfigField(STRING, "BASE_URL_AUTH", "accounts.spotify.com")
    }
}

android {
    namespace = modulePackageName
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
    }
}

task("testClasses").doLast {
    println("This is a dummy testClasses task")
}