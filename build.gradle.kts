@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.versions)
  alias(libs.plugins.vcu)
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**")
    ktfmt("0.33").googleStyle()
  }
  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**")
    ktfmt("0.33").googleStyle()
  }
}
