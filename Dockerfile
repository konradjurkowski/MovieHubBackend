FROM openjdk:17-jdk

EXPOSE 8080

COPY ./build/libs/moviehub_server-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app/

ENTRYPOINT ["java", "-jar", "moviehub_server-0.0.1-SNAPSHOT.jar"]
