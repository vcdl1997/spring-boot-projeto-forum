FROM openjdk:latest

WORKDIR /app

ENV FORUM_DATABASE_URL="jdbc:h2:mem:alura-forum"
ENV FORUM_DATABASE_USERNAME="sa"
ENV FORUM_DATABASE_PASSWORD=""
ENV FORUM_JWT_SECRET="1234"
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app/spring-app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "spring-app.jar"]