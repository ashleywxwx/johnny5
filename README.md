# Johnny 5 #

A slack bot using [Simple Slack API](https://github.com/Ullink/simple-slack-api), [Jenkins Client](https://github.com/RisingOak/jenkins-client), and Spring Boot

## Personalize ##

Create a application-local.yml overriding the default authentication for Slack and Jenkins

## Run ##

'mvn spring-boot:run -Dspring.profiles.active=local'

## Deploy Docker Image ##

'mvn deploy -Ddocker.skipDockerBuild=false'

Set environment variable DOCKER_HOST=tcp://hostname:2375 to run against a remote Docker host. Otherwise, will use localhost

## Register with Consul ##

Create a bootstrap.yml (see bootstrap-example.yml) and add the following properties:

    spring:
      cloud:
        consul:
          host: localhost
          port: 8500
