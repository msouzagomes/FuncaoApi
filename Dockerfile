FROM azul/zulu-openjdk-alpine:14.0.2
LABEL MAINTAINER Equipe DevOps devops@calcard.com.br

# Add the service itself
ADD target/ccred-api-*.jar /usr/share/api/app.jar

# Startup service
ENTRYPOINT ["java", "-jar", "/usr/share/api/app.jar", "-Djava.net.preferIPv4Stack=true"]