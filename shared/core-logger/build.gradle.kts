import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.ktlint)
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
            baseName = "core-logger"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Kermit
            implementation(libs.touchlab.kermit)

            // Koim
            implementation(libs.koin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

val modulePackageName = "com.spotify.app.core_logger.shared"

buildkonfig {
    packageName = modulePackageName
    exposeObjectWithName = "CoreLoggerBuildKonfig"

    defaultConfigs {
        buildConfigField(BOOLEAN, "DEBUG", "true")
    }

    defaultConfigs("debug") {
        buildConfigField(BOOLEAN, "DEBUG", "true")
    }

    defaultConfigs("release") {
        buildConfigField(BOOLEAN, "DEBUG", "false")
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
