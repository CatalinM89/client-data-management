FROM amazoncorretto:17
VOLUME /tmp
COPY demo-app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]