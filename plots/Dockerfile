FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY build/libs/plots-0.0.2-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
