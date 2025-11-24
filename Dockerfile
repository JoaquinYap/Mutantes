# ETAPA 1: BUILD
FROM alpine:latest as build
RUN apk update && apk add openjdk17
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# ETAPA 2: RUNTIME
FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build ./build/libs/Mutantes-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]