plugins {
  id("idea")
  id("java-library")
  id("maven-publish")
}

version = "3.0.0"

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
  testImplementation(platform("org.junit:junit-bom:5.10.2"))
  testImplementation("org.junit.jupiter:junit-jupiter")

  // performance testing dependencies
  testImplementation("org.openjdk.jmh:jmh-core:1.37")
  testAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
  testImplementation("com.google.code.gson:gson:2.8.9")
  testImplementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

tasks.javadoc {
  options.memberLevel = JavadocMemberLevel.PROTECTED
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

tasks.register("jmhBenchmark", JavaExec::class) {
  group = "verification"
  description = "Execute jmh benchmark comparisons"
  mainClass = "org.openjdk.jmh.Main"

  classpath = sourceSets.test.get().runtimeClasspath
}
