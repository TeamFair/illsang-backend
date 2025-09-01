FROM amazoncorretto:21

ENV TZ=Asia/Seoul

WORKDIR /app

COPY module-bootstrap/build/libs/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
