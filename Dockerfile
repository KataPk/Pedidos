FROM maven:3.9.4-eclipse-temurin-20 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM  openjdk:3.9.4-eclipse-temurin-20-alpine
COPY --from=build /target/Pedidos-0.0.1-SNAPSHOT.jar Pedidos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Pedidos.jar"]
