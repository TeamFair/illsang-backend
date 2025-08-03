# 1단계: 빌더 (Gradle + JDK)
FROM gradle:8.7-jdk21-alpine AS builder

WORKDIR /workspace
COPY . .

RUN ./gradlew :module-bootstrap:bootJar

# 2단계: 런타임 (JRE만)
FROM eclipse-temurin:21-jre-alpine
#FROM gcr.io/distroless/java17-debian11

WORKDIR /app

COPY --from=builder /workspace/module-bootstrap/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-XX:+UseSerialGC", "-Xms128m", "-Xmx512m", "-XX:MaxRAMPercentage=70.0", "-jar", "app.jar"]