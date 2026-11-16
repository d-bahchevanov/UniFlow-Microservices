FROM eclipse-temurin:21-jre-jammy

# Required for the curl healthcheck in docker-compose.yml
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/identity-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]