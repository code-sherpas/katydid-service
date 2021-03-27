FROM openjdk:11

COPY build/libs ./

CMD ["java", "-jar", "katydid-service.jar"]
