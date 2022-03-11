plugins {
  id("com.diffplug.spotless") version "6.3.0"
  id("com.github.ben-manes.versions") version "0.42.0"
  id("nl.littlerobots.version-catalog-update") version "0.3.1"
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
