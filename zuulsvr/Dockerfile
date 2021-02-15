FROM adoptopenjdk/openjdk8:alpine-jre
EXPOSE 5555
ARG JAR_FILE=target/zuulsvr-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
