val queryDslVersion = "5.1.0"

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    kotlin("plugin.allopen") version "1.9.25"
}

allprojects {
    group = "com.teamfair.illsang"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.plugin.jpa")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        // spring boot
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // database
        runtimeOnly("org.postgresql:postgresql")
        testRuntimeOnly("com.h2database:h2")

        // mysql
        runtimeOnly("mysql:mysql-connector-java")
        implementation("org.mariadb.jdbc:mariadb-java-client:2.4.0")
        implementation("com.zaxxer:HikariCP:5.0.1")

        // querydsl
        implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
        implementation("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
        kapt("jakarta.persistence:jakarta.persistence-api")
        kapt("jakarta.annotation:jakarta.annotation-api")

        // p6spy
        implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.10.0")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":core") {
    tasks.bootJar { enabled = false }
    tasks.jar { enabled = true }
}
