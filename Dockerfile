# Use official Eclipse Temurin JDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/arbitrary-arithmetic-1.0-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT [ "java", "-jar", "app.jar" ] 