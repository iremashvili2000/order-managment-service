# Start with a base OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Pass the JAR file path as a build argument

ARG SECOND_FILE=build/libs/order-managment-service.jar
COPY ${SECOND_FILE} second.jar


# Expose the port your app will run on
EXPOSE 9999

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/second.jar"]