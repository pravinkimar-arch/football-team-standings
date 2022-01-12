FROM openjdk:8-jdk-alpine
MAINTAINER PravinKumar
ARG JAR_FILE=target/footballstand-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} footballstand-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -Djasypt.encryptor.password="mVeMp14BBI5YOdnPLq7Yye0LA55r428y" -jar /opt/app/footballstand-0.0.1-SNAPSHOT.jar