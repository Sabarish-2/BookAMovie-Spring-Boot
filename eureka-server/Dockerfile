FROM maven:3.9.6-eclipse-temurin-17 AS build
# Set working directory
WORKDIR /app
COPY ..
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose Eureka default port
# EXPOSE 8761

# Run the Eureka server
ENTRYPOINT ["java", "-jar", "app.jar"]