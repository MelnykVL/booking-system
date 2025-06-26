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
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.liquibase:liquibase-core")
  implementation(libs.spring.doc.openapi)
  implementation(libs.mapstruct)
  implementation(libs.javafaker)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.mapstruct.processor)
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:postgresql")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
