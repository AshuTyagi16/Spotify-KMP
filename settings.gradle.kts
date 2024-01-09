dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }

    pluginManagement {
        repositories {
            google {
                mavenContent {
                    includeGroupByRegex(".*google.*")
                    includeGroupByRegex(".*android.*")
                }
            }
            mavenCentral()
            gradlePluginPortal()
        }
    }
}

rootProject.name = "Spotify-KMP"
include(":app")
include(":shared")
include(":shared:core-network")
include(":shared:core-logger")
include(":shared:core-preferences")
include(":shared:feature-homepage")
include(":shared:core-base")
include(":feature-homepage")
include(":shared:feature-playlist-detail")
include(":feature-playlist-detail")
