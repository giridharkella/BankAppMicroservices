# Use official Java 21 runtime
FROM eclipse-temurin:21-jre

MAINTAINER GSK

# Create app directory
WORKDIR /app

# Copy your jar
COPY target/accounts-0.1-SNAPSHOT.jar accounts-0.1-SNAPSHOT.jar

# Expose port (optional)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "accounts-0.1-SNAPSHOT.jar"]