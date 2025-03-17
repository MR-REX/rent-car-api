ARG JAR_FILE="rentcar-1.0.0.jar"

FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /build

COPY src src
COPY pom.xml pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package \
    dependency:copy-dependencies \
    -DincludeScope=runtime

ARG JAR_FILE

RUN jdeps \
    --ignore-missing-deps \
    -q \
    --recursive \
    --multi-release 17 \
    --print-module-deps \
    --class-path "target/dependency/*" \
    target/${JAR_FILE} > dependencies.info

RUN jlink \
    --add-modules $(cat dependencies.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output "assembled-jre"

FROM alpine:3.21.3

RUN apk add --no-cache curl

RUN addgroup -S spring-boot-group && adduser -S spring-boot -G spring-boot-group
USER spring-boot

VOLUME [ "/tmp" ]
WORKDIR /application

ARG JAR_FILE

COPY --from=build /build/assembled-jre assembled-jre
COPY --from=build /build/target/${JAR_FILE} application.jar

SHELL [ "/bin/ash", "-c" ]
ENTRYPOINT exec assembled-jre/bin/java ${JAVA_OPTIONS} -jar application.jar ${0} ${@}