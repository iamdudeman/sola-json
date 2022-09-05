plugins {
  id("idea")
  id("java-library")
  id("maven-publish")
}

version = "2.1.2"

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }

  withSourcesJar()
  withJavadocJar()
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(platform("org.junit:junit-bom:5.7.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "technology.sola.json"
      artifactId = "sola-json"
      version = version

      from(components["java"])
    }
  }
}

idea {
  module {
    isDownloadJavadoc = true
    isDownloadSources = true
  }
}
