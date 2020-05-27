FROM adoptopenjdk/openjdk11:alpine-jre

COPY ./build/libs/text-analyzer-service-1.0.0-SNAPSHOT.jar ./app.jar

EXPOSE 8886

CMD ["java", "-jar", "./app.jar"]