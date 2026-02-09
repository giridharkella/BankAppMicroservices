# Use official Java 21 runtime
FROM eclipse-temurin:21-jre

MAINTAINER GSK

# Create app directory
WORKDIR /app

# Copy your jar
COPY target/gateway-0.1-SNAPSHOT.jar gateway-0.1-SNAPSHOT.jar

# Expose port (optional)
EXPOSE 8070

# Run the application
ENTRYPOINT ["java", "-jar", "gateway-0.1-SNAPSHOT.jar"]