FROM openjdk:11

COPY build/libs /katydid-service
WORKDIR /katydid-service
EXPOSE 8080
CMD ["java", "-jar", "katydid-service.jar"]
