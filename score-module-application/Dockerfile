FROM openjdk:8-jdk-alpine
COPY ./target/backend-score-test.jar app.jar
ENTRYPOINT ["java", "-server", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev", "-jar", "/app.jar", "> /logs/application.log"]