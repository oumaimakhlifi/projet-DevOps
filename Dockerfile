FROM openjdk:8-jdk-alpine

EXPOSE 8089

ADD target/test-classes gestion-station-ski-1.0.jar

ENTRYPOINT ["java","-jar","/gestion-station-ski-1.0.jar"]
