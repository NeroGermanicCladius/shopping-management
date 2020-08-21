# Introduction
Shopping management web-application.
 
# Requirements
* Java 11
* Maven(3.6.0 or higher)
* Docker & Docker compose

# Installation
* Clone this project to your local machine.

If you are using Linux:
* Run `build.sh` script to compile sources, build project, generate docker images.
* Run `run.sh` script to run all services using docker-compose.
* If you want to stop project please run `stop.sh`, this will stop docker-compose.


Then: 
* Open `http://localhost:8080/swagger-ui.html` in your browser and have fun.

# Notes
The login endpoint is handled by a custom filter, that's why it's not shown in Swagger UI.
To log in, just make a POST request to `/login` url and provide `email` and `password` in request body.
You'll receive a newly generated JWT Token in your response header called `jwt-auth-token`.

# About the Project

This project directory contains all backend-related files and directories.
It's written in Java 11 and uses Spring Boot, Hibernate.

Maven is used here as a Dependency Manager and build tool.

1. `mvn clean package` command can be used to compile, test and build artifact for this project.

The Dockerfile for this service is based on azul/zulu-openjdk-alpine.

Due to the lack of provided time, not all requested features are implemented.
