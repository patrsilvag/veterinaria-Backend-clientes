# Imagen base Java 21
FROM eclipse-temurin:21-jdk-alpine

# Carpeta de trabajo
WORKDIR /app

# Copiar el JAR generado por Maven
COPY target/*.jar app.jar

# Puerto del Backend Clientes
EXPOSE 8082

# Ejecutar Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]