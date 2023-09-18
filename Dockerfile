FROM eclipse-temurin:20.0.2_9-jdk
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "–server.port=8083"]
EXPOSE 8083