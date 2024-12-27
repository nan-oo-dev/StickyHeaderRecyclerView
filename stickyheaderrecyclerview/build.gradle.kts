import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.nanoo.stickyheaderrecyclerview"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    libraryVariants.all {
        outputs.all {
            packageLibraryProvider {
                val separator = "_"
                val buildTypeName = buildType.name
                val versionName = "1.0.3"
                val date = Date()
                val formattedDate = SimpleDateFormat("ddMMyy_HHmm").format(date)
                val newAarName = "StickyHeaderRecyclerView$separator$formattedDate$separator$buildTypeName$separator$versionName.aar"
                archiveFileName.set(newAarName)
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.nanoo.developer"
            artifactId = "StickyHeaderRecyclerView"
            version = "1.0.3"
            pom{
                description = "Sticky Header Recycler View Library"
            }
        }
    }
    repositories{
        mavenLocal()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}