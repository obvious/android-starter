pluginManagement {
  val agpVersion = "7.1.2"
  val kotlinVersion = "1.6.10"
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
  plugins {
    id("com.android.application") version agpVersion
    id("com.android.library") version agpVersion
    id("org.jetbrains.kotlin.android") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "Android Starter"

include(":app")
