FROM openjdk:19
COPY src/main/resources/players.csv /app/players.csv
ADD target/docker-producer-spring-boot.jar docker-producer-spring-boot.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "docker-producer-spring-boot.jar"]