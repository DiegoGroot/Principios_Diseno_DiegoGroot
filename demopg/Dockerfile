FROM openjdk:22-rc-oracle

LABEL maintainer="alejandrogrootc@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/demopg-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./unrandom","-jar","/app.jar"]

