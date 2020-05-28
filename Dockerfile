FROM adoptopenjdk/openjdk11:alpine-jre

COPY build/libs/text-analyzer-service-1.0.0-SNAPSHOT.jar /app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar