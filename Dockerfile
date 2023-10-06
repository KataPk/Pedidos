FROM maven:3.9.4-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package  -DskipTests

FROM  openjdk:17-jdk-slim
COPY --from=build /target/Pedidos-0.0.1-SNAPSHOT.jar Pedidos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Pedidos.jar"]



