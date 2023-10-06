FROM maven:3.9.4-eclipse-temurin-20 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

FROM  openjdk:20-jdk-slim
COPY --from=build /target/Pedidos-0.0.1-SNAPSHOT.war Pedidos.war
EXPOSE 8080
ENTRYPOINT ["java", "-war", "Pedidos.war"]
