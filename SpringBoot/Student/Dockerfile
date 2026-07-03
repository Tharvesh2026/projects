FROM eclipse-temurin:25-jdk
LABEL author = "@imtharvesh"
WORKDIR /app

COPY target/student.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]