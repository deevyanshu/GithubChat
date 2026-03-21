# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Optimization: Copy pom first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Ensure we only copy the actual executable fat jar
COPY --from=build /app/target/*.jar app.jar

# Cloud Run ignores EXPOSE but it's good for documentation
EXPOSE 8080

# The Fix: Use the shell to ensure ${PORT} is expanded correctly
# and add flags to speed up serverless startup
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -jar app.jar"]
