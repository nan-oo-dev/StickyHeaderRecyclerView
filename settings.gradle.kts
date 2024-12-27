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
        gradlePluginPortal()


        mavenLocal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()


        mavenLocal()
        maven {  url = uri("https://jitpack.io")}

    }
}

rootProject.name = "StickyHeaderRecyclerView"
include(":app")
include(":stickyheaderrecyclerview")
