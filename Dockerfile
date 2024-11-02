FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/gestion-station-ski-1.0.jar td.jar
ENTRYPOINT ["java","-jar","/td.jar"]
