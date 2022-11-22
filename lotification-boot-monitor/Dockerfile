FROM openjdk:20-ea-17-jdk

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar

COPY $JAR_FILE app.jar

ENV PROFILE local

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]