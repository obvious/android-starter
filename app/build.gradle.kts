@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
}

android {
  compileSdk = 31

  defaultConfig {
    applicationId = "in.obvious.android.starter"
    minSdk = 21
    targetSdk = 31
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures { viewBinding = true }
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions { jvmTarget = JavaVersion.VERSION_11.toString() }
}

dependencies {
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.androidx.corektx)
  implementation(libs.androidx.lifecycle.common)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  implementation(libs.mobius.android)
  testImplementation(libs.mobius.test)

  testImplementation(libs.truth)
}
