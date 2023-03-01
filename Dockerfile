FROM java:8-alpine
ADD target/ice-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT java -jar app.jar  --spring.profiles.active=prod