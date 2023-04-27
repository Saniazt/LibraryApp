FROM openjdk:17
EXPOSE 8080
ADD target/library-app-images.jar library-app-images.jar
ENTRYPOINT ["java","-jar","/library-app-images.jar"]