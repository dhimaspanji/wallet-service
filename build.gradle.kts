plugins {
	java
	id("org.springframework.boot") version "3.5.10"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "co.id.gpay"
version = "0.0.1-SNAPSHOT"
description = "Wallet Service for GPay Technical Test"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")

    compileOnly("io.soabase.record-builder:record-builder-core:44")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.soabase.record-builder:record-builder-processor:44")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
