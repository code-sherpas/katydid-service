FROM gradle:6.8.2-jdk11 AS build

COPY --chown=gradle:gradle . /katydid-service/gradle/src

WORKDIR /katydid-service/gradle/src

RUN gradle build --no-daemon

FROM openjdk:11

RUN mkdir /katydid-service

COPY --from=build /katydid-service/gradle/src/build/libs/ ./

ENTRYPOINT ["java","-jar","katydid-service.jar"]