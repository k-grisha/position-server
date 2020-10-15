#FROM openjdk:11-jdk-slim as runtime
#FROM adoptopenjdk:11-jre-hotspot
FROM arm32v7/adoptopenjdk:11.0.8_10-jdk-hotspot-bionic
COPY target/point-server-*.jar point-server.jar
EXPOSE 8010
CMD java ${JAVA_OPTS} -jar point-server.jar