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
