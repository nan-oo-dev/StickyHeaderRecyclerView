Add it in your root build.gradle at the end of repositories:
 
Step 1. Add the maven for jitpack.io

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
 
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.nan-oo-dev:StickyHeaderRecyclerView:1.0.3'
	}
