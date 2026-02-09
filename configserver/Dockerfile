# Use official Java 21 runtime
FROM eclipse-temurin:21-jre

MAINTAINER GSK

# Create app directory
WORKDIR /app

# Copy your jar
COPY target/configserver-0.1-SNAPSHOT.jar configserver-0.1-SNAPSHOT.jar

# Expose port (optional)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "configserver-0.1-SNAPSHOT.jar"]