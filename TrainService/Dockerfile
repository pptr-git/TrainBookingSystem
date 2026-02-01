#FROM eclipse-temurin:17-jdk-alpine
#
#WORKDIR /app
#
#COPY TrainService/target/*.jar app.jar
#
#EXPOSE 8081
#
# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /build
COPY TrainService/pom.xml .
RUN mvn dependency:go-offline

COPY TrainService/src ./src
RUN mvn clean package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]

