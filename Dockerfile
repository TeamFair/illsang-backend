FROM amazoncorretto:21

WORKDIR /app

COPY module-bootstrap/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
