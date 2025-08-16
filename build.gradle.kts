plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.devtools.ksp") version "1.9.25-1.0.20"
}

group = "com.illsang"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

extra["springCloudVersion"] = "2024.0.1"

repositories {
	mavenCentral()
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = false
}

tasks.named<Jar>("jar") {
	enabled = true
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply the Spring Boot plugin to all subprojects
subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "com.google.devtools.ksp")

	repositories {
		mavenCentral()
	}

	configurations {
		maybeCreate("developmentOnly")
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(21)
		}
	}

	dependencies {
		// Spring Data (RDB, Redis)
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")

		// Spring Security + OAuth2
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
		implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
		implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
		testImplementation("org.springframework.security:spring-security-test")

		// OAuth Token Validation
		implementation("com.google.api-client:google-api-client:2.2.0")
		implementation("io.jsonwebtoken:jjwt-api:0.12.3")
		implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
		implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

		// Spring Web
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("org.springframework.cloud:spring-cloud-starter-gateway-mvc")

		// Kotlin + Jackson + Reactor
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

		// Lombok
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		// AWS
		implementation("software.amazon.awssdk:s3:2.32.2")
		implementation("io.awspring.cloud:spring-cloud-aws-starter-parameter-store:3.4.0")

		// QueryDSL
		val querydslVersion = "7.0"
		implementation("io.github.openfeign.querydsl:querydsl-jpa:${querydslVersion}")
		annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:${querydslVersion}:jpa")
		annotationProcessor("jakarta.persistence:jakarta.persistence-api")
		ksp("io.github.openfeign.querydsl:querydsl-ksp-codegen:$querydslVersion")

		// 개발용 도구
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
		developmentOnly("org.springframework.boot:spring-boot-devtools")
		testImplementation("com.github.codemonstur:embedded-redis:1.4.3")
		implementation("org.springframework.boot:spring-boot-starter-actuator")

		// DB Driver
		runtimeOnly("com.h2database:h2")
		runtimeOnly("org.postgresql:postgresql")

		// 테스트
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.projectreactor:reactor-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:${rootProject.extra["springCloudVersion"]}")
		}
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	allOpen {
		annotation("jakarta.persistence.Entity")
		annotation("jakarta.persistence.MappedSuperclass")
		annotation("jakarta.persistence.Embeddable")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
