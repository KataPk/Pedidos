#FROM maven:3.9.4-eclipse-temurin-17 AS build
#COPY . .
#RUN mvn clean package -Pprod -DskipTests

#FROM  openjdk:17-jdk-slim
#COPY --from=build /target/Pedidos-0.0.1-SNAPSHOT.war Pedidos.war
#EXPOSE 8080
#ENTRYPOINT ["java", "-war", "Pedidos.war"]


FROM tomcat:latest
ADD target/Pedidos.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]