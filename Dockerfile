# 1단계: Gradle 빌더
FROM gradle:8.7-jdk17 AS builder

WORKDIR /build
COPY . .

# --no-daemon 옵션: 데몬 모드 비활성화 (CI/CD 환경에서 추천)
RUN gradle bootJar --no-daemon

# 2단계: 실제 실행 이미지
FROM amazoncorretto:17

WORKDIR /app
COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]