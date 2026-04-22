FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-rc-oracle
LABEL maintainer="alejandrogrootc@gmail.com"
VOLUME /tmp
EXPOSE 8080

# Copy the JAR from build stage
COPY --from=build /app/target/android_proyect-0.0.1-SNAPSHOT.jar app.jar

# Set default profile to production, can be overridden
ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=${PORT}", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]