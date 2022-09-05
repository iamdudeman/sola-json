plugins {
  id("java-library")
}

version = "2.1.1"

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
