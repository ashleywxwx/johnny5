# Johnny 5 #

A slack bot using [Simple Slack API](https://github.com/Ullink/simple-slack-api) and Spring Boot

## Run ##

'mvn spring-boot:run'

## Deploy Docker Image ##

'mvn deploy -Ddocker.skipDockerBuild=false'

Set environment variable DOCKER_HOST=tcp://hostname:2375 to run against a remote Docker host. Otherwise, will use localhost