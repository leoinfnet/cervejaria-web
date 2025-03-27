FROM amazoncorretto:17
LABEL authors="leonardogloria"
WORKDIR /app
COPY docker/cervejaria-acme-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]