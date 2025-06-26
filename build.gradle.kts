plugins {
  java
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.dependency.management)
}

group = "com.testtask"
version = "0.0.1"

val javaVersion = providers.gradleProperty("javaVersion").map(String::toInt)

java {
  toolchain {
    languageVersion = javaVersion.map(JavaLanguageVersion::of)
    vendor = JvmVendorSpec.ADOPTIUM
  }
}

repositories {
  mavenCentral()
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {
  // Spring Boot starters
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")

  // Database
  runtimeOnly("org.postgresql:postgresql")
  implementation("org.liquibase:liquibase-core")

  // Utilities
  implementation(libs.spring.doc.openapi)
  implementation(libs.mapstruct)
  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  implementation(libs.javafaker) { exclude("org.yaml") }
  implementation("org.yaml:snakeyaml")

  // Testing
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:postgresql")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
