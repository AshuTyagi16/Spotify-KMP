@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.spotify.app.feature_playlist_detail"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = libs.versions.isMinifyEnabled.get().toBooleanStrict()
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTargetVersion.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {

    // Core-UI Module
    implementation(project(":core-ui"))

    // Shared Feature Playlist Detail Module
    implementation(project(":shared:feature-playlist-detail"))

    // Compose Shimmer
    implementation(libs.compose.shimmer)

    // Coil (Image Loading)
    implementation(libs.coil)

    // Paging compose
    implementation(libs.paging.compose)

    // Core Ktx
    implementation(libs.core.ktx)

    // Lifecycle Ktx
    implementation(libs.lifecycle.runtime.ktx)

    // Koin
    implementation(libs.koin)
    implementation(libs.koin.android)

    //Compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}