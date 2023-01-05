FROM openjdk:19
ADD target/docker-consumer-spring-boot.jar docker-consumer-spring-boot.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-consumer-spring-boot.jar"]