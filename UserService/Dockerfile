#FROM eclipse-temurin:17-jdk-alpine
#
#WORKDIR /app
#
#COPY UserService/target/*.jar app.jar
#
#EXPOSE 8083
#
#ENTRYPOINT ["java","-jar","app.jar"]

# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /build
COPY UserService/pom.xml .
RUN mvn dependency:go-offline

COPY UserService/src ./src
RUN mvn clean package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]

