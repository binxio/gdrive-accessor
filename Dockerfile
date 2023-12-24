FROM eclipse-temurin:21-jdk-alpine as build

WORKDIR /app
ENV MAVEN_OPTS="-Dmaven.repo.local=/app/.m2/repository"
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw --batch-mode --no-transfer-progress dependency:resolve dependency:resolve-plugins dependency:go-offline
COPY src src

RUN ./mvnw install  -DskipTests

FROM eclipse-temurin:21-jre-alpine
COPY --from=build /app/target/app.jar /
ENTRYPOINT ["java","-jar","app.jar"]
