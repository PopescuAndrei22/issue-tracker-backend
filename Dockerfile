# --- Stage 1: Build and extract layers ---
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Cache dependencies first (improves build speed)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Extract layers using Spring Boot's built-in layertools
RUN java -Djarmode=layertools -jar target/*.jar extract

# --- Stage 2: Final lightweight runtime ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-root system user for security
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copy the extracted layers from the builder stage
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# Expose application port
EXPOSE 8080

# Configure logging directory permissions for non-root user
VOLUME /app/logs

# Use Spring Boot 3.2+ optimized JarLauncher
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]