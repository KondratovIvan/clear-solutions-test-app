FROM openjdk:21-jdk-slim
COPY target/clear-solution-test-app-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
CMD ["java","-jar","/clear-solution-test-app-0.0.1-SNAPSHOT.jar"]
