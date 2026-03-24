FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /app
COPY demopg/ .
RUN mvn clean package -DskipTests

FROM openjdk:22-rc-oracle
LABEL maintainer="alejandrogrootc@gmail.com"
VOLUME /tmp
EXPOSE 8080
COPY --from=build /app/target/demopg-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
