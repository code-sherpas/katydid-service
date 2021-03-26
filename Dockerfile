FROM gradle:6.8.2-jdk11-openj9

COPY ./ ./

RUN ./gradlew bootJar

EXPOSE 8080
CMD ["java", "-jar", "build/libs/katydid-service.jar"]
