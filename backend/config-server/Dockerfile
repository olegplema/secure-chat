FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src /app/src

RUN mvn package

FROM openjdk:17-slim

RUN apt-get update && \
    apt-get install -y curl

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]

