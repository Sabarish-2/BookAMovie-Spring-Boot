# Use an official OpenJDK image as base
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/eureka-server-*.jar eureka-server.jar

# Expose Eureka default port
EXPOSE 8761

# Run the Eureka server
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]