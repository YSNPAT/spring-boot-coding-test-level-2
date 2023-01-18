FROM openjdk:11
EXPOSE 8080
ADD target/spring-boot-coding-test-level-0.0.1-SNAPSHOT.jar
ENTRYPOINT["java", "jar", "spring-boot-coding-test-level-2.jar"]