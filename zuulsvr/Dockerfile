FROM openjdk:11

ADD target/zuulsvr-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 5555