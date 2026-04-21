FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
# ETAPA 1: Construcción
FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución
FROM openjdk:22-rc-oracle
LABEL maintainer="alejandrogrootc@gmail.com"
VOLUME /tmp
EXPOSE 8080
# Asegúrate que el nombre del JAR coincida con el generado en tu pom.xml
COPY --from=build /app/target/android_proyect-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
