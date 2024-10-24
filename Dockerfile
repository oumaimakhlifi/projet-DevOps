FROM openjdk:1.8-jdk-alpine

EXPOSE 8089

ADD target/gestion-station-ski-1.0.jar timesheet-devops-1.0.jar

ENTRYPOINT ["java","-jar","/gestion-station-ski-1.0.jar"]
