# Usamos Amazon Corretto 17 como base
FROM amazoncorretto:17

# Directorio de trabajo
WORKDIR /app

# Copiamos cualquier JAR que esté en target/, renombrándolo a app.jar
COPY target/*.jar app.jar

# Exponemos el puerto del backend
EXPOSE 8080

# Arrancamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
