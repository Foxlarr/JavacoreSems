# Dockerfile
FROM openjdk:19

WORKDIR /app

COPY ./src /app/src

RUN mkdir -p out && javac -d out src/main/java/com/example/app/MainClass.java

CMD ["java", "-cp", "out", "com.example.app.MainClass"]
