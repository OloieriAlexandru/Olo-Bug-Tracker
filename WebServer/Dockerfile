FROM openjdk:8-jdk-alpine
EXPOSE 9000
ARG JAR_FILE=target/olo-bug-tracker-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Xmx256m","-XX:+UseContainerSupport","/app.jar"]