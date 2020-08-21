#!/bin/bash

# Build Backend
mvn clean package

# Build Backend Docker Image
docker build -t shopping-management:only .

# CD back to root directory
cd ..
