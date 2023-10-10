FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spotify-service.jar

ENV client-id=b1bec7cbb94b45ae9d1807fab22b0d2c
ENV client-secret=bb615d8e3f564bce97001c8cc0399d9e
ENTRYPOINT ["java", "-jar", "/app/spotify-service.jar"]