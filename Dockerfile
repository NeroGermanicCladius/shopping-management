FROM azul/zulu-openjdk-alpine:11.0.8-jre

ADD target/shopping-management.jar /target.jar

CMD ["java", "-jar", "/target.jar"]
EXPOSE 8080
