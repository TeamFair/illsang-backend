FROM eclipse-temurin:21-jdk

# 작업 디렉토리 생성
WORKDIR /app

# 루트 프로젝트에서 빌드된 JAR 복사 (Gradle bootJar 기준)
COPY build/libs/module-bootstrap-*.jar app.jar

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]