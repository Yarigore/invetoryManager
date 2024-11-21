FROM openjdk:22-jdk-slim
LABEL authors="dimas"

COPY target/invetoryManager-0.0.2-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
