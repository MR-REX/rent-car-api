FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /build

COPY src src
COPY pom.xml pom.xml

RUN --mount=type=cache,target=/root/.m2 mvn clean package dependency:copy-dependencies -DincludeScope=runtime -DskipTests

FROM eclipse-temurin:17-jre-alpine-3.21

RUN apk add --no-cache curl

RUN addgroup -S spring-boot-group && adduser -S spring-boot -G spring-boot-group
USER spring-boot

VOLUME [ "/tmp" ]
WORKDIR /application

ARG JAR_FILE="rentcar-1.0.0.jar"
COPY --from=build /build/target/${JAR_FILE} application.jar

SHELL [ "/bin/ash", "-c" ]
ENTRYPOINT exec java ${JAVA_OPTIONS} -jar application.jar ${0} ${@}