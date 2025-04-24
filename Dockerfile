# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine

# Install git
RUN apk update && apk add --no-cache git

WORKDIR /app
COPY --from=builder /app/target/hostName-0.0.1-SNAPSHOT.jar hostName.jar

CMD ["java", "-jar", "hostName.jar"]