FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY module-bootstrap/build/libs/module-bootstrap-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
