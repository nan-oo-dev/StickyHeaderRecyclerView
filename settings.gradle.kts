pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven {
            url = uri( "https://github.com/QuickBlox/quickblox-android-sdk-releases/raw/master/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://jitpack.io")
        }
        maven { url = uri("https://maven.google.com") }
        maven {
            url = uri( "https://github.com/QuickBlox/quickblox-android-sdk-releases/raw/master/")
        }
    }
}

rootProject.name = "StickyHeaderRecycler"
include(":app")
include(":stickyheaderrecyclerview")
