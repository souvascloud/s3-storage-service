FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build /app/target/s3-storage-service-0.0.1-SNAPSHOT.jar app.jar

RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

# JVM options
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseStringDeduplication"

# Run app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
