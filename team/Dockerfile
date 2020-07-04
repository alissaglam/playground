FROM openjdk:8-jdk-alpine

COPY target/team-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4345

ENTRYPOINT ["java", "-Dspring.profiles.active=k8n",  "-jar", "/app.jar"]
