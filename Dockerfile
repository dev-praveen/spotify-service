FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/spotify-service-0.0.1-SNAPSHOT spotify-service.jar

ENV client-id=b1bec7cbb94b45ae9d1807fab22b0d2c
ENV client-secret=bb615d8e3f564bce97001c8cc0399d9e

ENTRYPOINT ["java", "-jar", "spotify-service.jar"]