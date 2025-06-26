# Stage 1: Compilación con Maven y producción de recursos Vaadin
FROM maven:3.8.1-openjdk-17-slim AS builder

WORKDIR /usr/src/app

# Copiamos pom y código
COPY pom.xml .
COPY src ./src

# Limpiamos, preparamos frontend y empaquetamos en modo producción
RUN mvn clean package -Pproduction -DskipTests \
    && mvn vaadin:build-frontend -Pproduction

# Stage 2: Imagen de runtime
FROM amazoncorretto:17
WORKDIR /app

# Copiamos el JAR ya construido (tiene los recursos prod incluidos)
COPY --from=builder /usr/src/app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
