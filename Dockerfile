FROM azul/zulu-openjdk-alpine:11.0.7-jre

ADD target/shopping-management.jar /target.jar

CMD ["java", "-jar", "/target.jar"]
EXPOSE 8080
