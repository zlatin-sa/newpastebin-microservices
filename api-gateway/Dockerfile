FROM eclipse-temurin:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "/app.jar"]