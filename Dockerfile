FROM bellsoft/liberica-openjdk-alpine-musl:17.0.7
COPY ./target/ex-ms-demo-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","ex-ms-demo-0.0.1-SNAPSHOT.jar"]